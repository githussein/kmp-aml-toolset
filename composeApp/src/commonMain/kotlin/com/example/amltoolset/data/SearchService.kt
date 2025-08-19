package com.example.amltoolset.data

import com.example.aml_kyc_tool.data.PersonRepository
import com.example.aml_kyc_tool.data.model.PersonDto
import com.example.amltoolset.data.model.SearchResult

class SearchService(
    private val localRepository: PersonRepository,
    private val unRepository: UNSanctionsRepository
) {
    suspend fun searchAllSources(query: String): List<SearchResult> {
        val localResults = localRepository.getPersons()
            .filterByQuery(query)
            .map { SearchResult.LocalPersonResult(it) }

        val unIndividualResults = unRepository.searchUNIndividuals(query)
            .map { SearchResult.UNIndividualResult(it) }

        val unEntityResults = unRepository.searchUNEntities(query)
            .map { SearchResult.UNEntityResult(it) }

        return localResults + unIndividualResults + unEntityResults
    }

    suspend fun searchLocalOnly(query: String): List<SearchResult> {
        return localRepository.getPersons()
            .filterByQuery(query)
            .map { SearchResult.LocalPersonResult(it) }
    }

    suspend fun searchUNOnly(query: String): List<SearchResult> {
        val individuals = unRepository.searchUNIndividuals(query)
            .map { SearchResult.UNIndividualResult(it) }

        val entities = unRepository.searchUNEntities(query)
            .map { SearchResult.UNEntityResult(it) }

        return individuals + entities
    }

    private fun List<PersonDto>.filterByQuery(query: String): List<PersonDto> {
        if (query.isBlank()) return emptyList()
        return this.filter { person ->
            person.fullNameLatin.contains(query, ignoreCase = true) ||
                    person.fullNameArabic.contains(query, ignoreCase = true) ||
                    person.documentNumber.contains(query, ignoreCase = true)
        }
    }
}