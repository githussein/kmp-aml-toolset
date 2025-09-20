package com.example.amltoolset.data

import com.example.amltoolset.data.model.UNConsolidatedList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

import com.example.amltoolset.data.model.UNIndividual
import com.example.amltoolset.data.model.UNEntity
import com.example.amltoolset.data.model.UNIndividuals
import com.example.amltoolset.data.model.UNEntities
import com.example.amltoolset.data.model.UNIndividualDocument
import com.example.amltoolset.data.model.UNNationality

object XMLParser {
    fun parseUNSanctions(xmlText: String): UNConsolidatedList {
        return try {
            val individuals = parseIndividuals(xmlText)
            val entities = parseEntities(xmlText)

            println("Parsed ${individuals.size} individuals, ${entities.size} entities")

            UNConsolidatedList(
                INDIVIDUALS = UNIndividuals(individuals),
                ENTITIES = UNEntities(entities)
            )
        } catch (e: Exception) {
            println("Simple XML parsing failed: ${e.message}")
            UNConsolidatedList()
        }
    }

    private fun parseIndividuals(xmlText: String): List<UNIndividual> {
        val individuals = mutableListOf<UNIndividual>()
        var index = 0

        while (index < xmlText.length) {
            val start = xmlText.indexOf("<INDIVIDUAL>", index)
            if (start == -1) break

            val end = xmlText.indexOf("</INDIVIDUAL>", start)
            if (end == -1) break

            val individualXml = xmlText.substring(start, end + 13)
            val individual = parseIndividual(individualXml)
            individuals.add(individual)

            index = end + 13
        }

        return individuals
    }

    private fun parseIndividual(xml: String): UNIndividual {
        return UNIndividual(
            FIRST_NAME = extractTagValue(xml, "FIRST_NAME"),
            SECOND_NAME = extractTagValue(xml, "SECOND_NAME"),
            REFERENCE_NUMBER = extractTagValue(xml, "REFERENCE_NUMBER"),
            UN_LIST_TYPE = extractTagValue(xml, "UN_LIST_TYPE"),
            LISTED_ON = extractTagValue(xml, "LISTED_ON"),
            GENDER = extractTagValue(xml, "GENDER"),
            COMMENTS1 = extractTagValue(xml, "COMMENTS1"),
            NATIONALITY = UNNationality(extractTagValue(xml, "NATIONALITY", "VALUE")),
            // Add more fields when needed
            INDIVIDUAL_DOCUMENT = parseDocuments(xml) // Parse documents if needed
        )
    }

    private fun parseDocuments(xml: String): List<UNIndividualDocument> {
        val documents = mutableListOf<UNIndividualDocument>()
        var index = 0

        while (index < xml.length) {
            val start = xml.indexOf("<INDIVIDUAL_DOCUMENT>", index)
            if (start == -1) break

            val end = xml.indexOf("</INDIVIDUAL_DOCUMENT>", start)
            if (end == -1) break

            val docXml = xml.substring(start, end + 22)
            documents.add(
                UNIndividualDocument(
                    TYPE_OF_DOCUMENT = extractTagValue(docXml, "TYPE_OF_DOCUMENT"),
                    NUMBER = extractTagValue(docXml, "NUMBER"),
                    ISSUING_COUNTRY = extractTagValue(docXml, "ISSUING_COUNTRY")
                )
            )

            index = end + 22
        }

        return documents
    }

    // Helper for nested values like <NATIONALITY><VALUE>...</VALUE></NATIONALITY>
    private fun extractTagValue(xml: String, parentTag: String, childTag: String): String? {
        val parentStart = xml.indexOf("<$parentTag>")
        if (parentStart == -1) return null

        val parentEnd = xml.indexOf("</$parentTag>", parentStart)
        if (parentEnd == -1) return null

        val parentContent = xml.substring(parentStart, parentEnd + parentTag.length + 3)
        return extractTagValue(parentContent, childTag)
    }

    private fun parseEntities(xmlText: String): List<UNEntity> {
        val entities = mutableListOf<UNEntity>()
        var index = 0

        while (index < xmlText.length) {
            val start = xmlText.indexOf("<ENTITY>", index)
            if (start == -1) break

            val end = xmlText.indexOf("</ENTITY>", start)
            if (end == -1) break

            val entityXml = xmlText.substring(start, end + 9)
            val entity = parseEntity(entityXml)
            entities.add(entity)

            index = end + 9
        }

        return entities
    }

    private fun parseEntity(xml: String): UNEntity {
        return UNEntity(
            FIRST_NAME = extractTagValue(xml, "FIRST_NAME"),
            REFERENCE_NUMBER = extractTagValue(xml, "REFERENCE_NUMBER")
            // Add more fields as needed
        )
    }

    private fun extractTagValue(xml: String, tagName: String): String? {
        val startTag = "<$tagName>"
        val endTag = "</$tagName>"

        val start = xml.indexOf(startTag)
        if (start == -1) return null

        val end = xml.indexOf(endTag, start)
        if (end == -1) return null

        val valueStart = start + startTag.length
        return xml.substring(valueStart, end).trim()
    }
}

interface UNSanctionsService {
    suspend fun getUNSanctionsList(): UNConsolidatedList
}

class UNSanctionsServiceImpl(private val client: HttpClient) : UNSanctionsService {
    override suspend fun getUNSanctionsList(): UNConsolidatedList {
        return try {
            val xmlText = client.get("https://scsanctions.un.org/resources/xml/en/consolidated.xml")
                .body<String>()

            println("XML downloaded: ${xmlText.length} bytes")

            // Use our simple parser
            XMLParser.parseUNSanctions(xmlText)

        } catch (e: Exception) {
            println("UN sanctions failed: ${e.message}")
            UNConsolidatedList()
        }
    }
}