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
        val individuals = unRepository.getUNSanctions().INDIVIDUALS?.INDIVIDUAL ?: emptyList()
        val entities = unRepository.getUNSanctions().ENTITIES?.ENTITY ?: emptyList()

        val filteredIndividuals = individuals.filter { individual ->
            individual.FIRST_NAME?.contains(query, ignoreCase = true) == true ||
                    individual.SECOND_NAME?.contains(query, ignoreCase = true) == true ||
                    individual.REFERENCE_NUMBER?.contains(query, ignoreCase = true) == true ||
                    individual.COMMENTS1?.contains(query, ignoreCase = true) == true ||
                    individual.INDIVIDUAL_DOCUMENT.any {
                        it.NUMBER?.contains(query, ignoreCase = true) == true
                    }
        }

        val filteredEntities = entities.filter { entity ->
            entity.FIRST_NAME?.contains(query, ignoreCase = true) == true ||
                    entity.REFERENCE_NUMBER?.contains(query, ignoreCase = true) == true ||
                    entity.COMMENTS1?.contains(query, ignoreCase = true) == true
        }

        return filteredIndividuals.map { SearchResult.UNIndividualResult(it) } +
                filteredEntities.map { SearchResult.UNEntityResult(it) }
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