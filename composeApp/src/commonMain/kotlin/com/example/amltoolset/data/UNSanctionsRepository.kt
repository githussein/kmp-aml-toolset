package com.example.amltoolset.data

import com.example.amltoolset.data.model.UNEntity
import com.example.amltoolset.data.model.UNIndividual

interface UNSanctionsRepository {
    suspend fun getUNIndividuals(): List<UNIndividual>
    suspend fun getUNEntities(): List<UNEntity>
    suspend fun searchUNIndividuals(query: String): List<UNIndividual>
    suspend fun searchUNEntities(query: String): List<UNEntity>
}

class UNSanctionsRepositoryImpl(
    private val unSanctionsService: UNSanctionsService
) : UNSanctionsRepository {

    override suspend fun getUNIndividuals(): List<UNIndividual> {
        return try {
            val unList = unSanctionsService.getUNSanctionsList()
            unList.INDIVIDUALS?.INDIVIDUAL ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getUNEntities(): List<UNEntity> {
        return try {
            val unList = unSanctionsService.getUNSanctionsList()
            unList.ENTITIES?.ENTITY ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchUNIndividuals(query: String): List<UNIndividual> {
        val individuals = getUNIndividuals()
        return if (query.isBlank()) emptyList() else {
            individuals.filter { individual ->
                individual.FIRS_NAME?.contains(query, ignoreCase = true) == true ||
                        individual.SECOND_NAME?.contains(query, ignoreCase = true) == true ||
                        individual.REFERENCE_NUMBER?.contains(query, ignoreCase = true) == true ||
                        individual.NATIONALITY?.VALUE?.contains(query, ignoreCase = true) == true ||
                        individual.INDIVIDUAL_ALIAS.any { it.ALIAS_NAME?.contains(query, ignoreCase = true) == true }
            }
        }
    }

    override suspend fun searchUNEntities(query: String): List<UNEntity> {
        val entities = getUNEntities()
        return if (query.isBlank()) emptyList() else {
            entities.filter { entity ->
                entity.FIRST_NAME?.contains(query, ignoreCase = true) == true ||
                        entity.REFERENCE_NUMBER?.contains(query, ignoreCase = true) == true ||
                        entity.ENTITY_ALIAS.any { it.ALIAS_NAME?.contains(query, ignoreCase = true) == true }
            }
        }
    }
}