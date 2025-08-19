package com.example.amltoolset.data.model

import com.example.aml_kyc_tool.data.model.PersonDto

sealed class SearchResult {
    data class LocalPersonResult(val person: PersonDto) : SearchResult()
    data class UNIndividualResult(val individual: UNIndividual) : SearchResult()
    data class UNEntityResult(val entity: UNEntity) : SearchResult()
}