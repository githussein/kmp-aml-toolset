package com.example.amltoolset.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UNConsolidatedList(
    val INDIVIDUALS: UNIndividuals? = null,
    val ENTITIES: UNEntities? = null
)

@Serializable
data class UNIndividuals(
    val INDIVIDUAL: List<UNIndividual> = emptyList()
)

@Serializable
data class UNEntities(
    val ENTITY: List<UNEntity> = emptyList()
)

@Serializable
data class UNIndividual(
    val DATAID: String? = null,
    val FIRST_NAME: String? = null, // Fixed the typo from FIRS_NAME
    val SECOND_NAME: String? = null,
    val UN_LIST_TYPE: String? = null,
    val REFERENCE_NUMBER: String? = null,
    val LISTED_ON: String? = null,
    val GENDER: String? = null,
    val COMMENTS1: String? = null,
    val NATIONALITY: UNNationality? = null,
    val LIST_TYPE: UNListType? = null,
    val LAST_DAY_UPDATED: UNLastDayUpdated? = null,
    val INDIVIDUAL_ALIAS: List<UNIndividualAlias> = emptyList(),
    val INDIVIDUAL_ADDRESS: List<UNIndividualAddress> = emptyList(),
    val INDIVIDUAL_DATE_OF_BIRTH: List<UNIndividualDateOfBirth> = emptyList(),
    val INDIVIDUAL_PLACE_OF_BIRTH: List<UNIndividualPlaceOfBirth> = emptyList(),
    val INDIVIDUAL_DOCUMENT: List<UNIndividualDocument> = emptyList(),
    val SORT_KEY: String? = null,
    val SORT_KEY_LAST_MOD: String? = null
)

@Serializable
data class UNEntity(
    val DATAID: String? = null,
    val FIRST_NAME: String? = null,
    val UN_LIST_TYPE: String? = null,
    val REFERENCE_NUMBER: String? = null,
    val LISTED_ON: String? = null,
    val COMMENTS1: String? = null,
    val LIST_TYPE: UNListType? = null,
    val LAST_DAY_UPDATED: UNLastDayUpdated? = null,
    val ENTITY_ALIAS: List<UNEntityAlias> = emptyList(),
    val ENTITY_ADDRESS: List<UNEntityAddress> = emptyList(),
    val SORT_KEY: String? = null,
    val SORT_KEY_LAST_MOD: String? = null
)

@Serializable
data class UNNationality(
    val VALUE: String? = null
)

@Serializable
data class UNListType(
    val VALUE: String? = null
)

@Serializable
data class UNLastDayUpdated(
    val VALUE: List<String> = emptyList()
)

@Serializable
data class UNIndividualAlias(
    val QUALITY: String? = null,
    val ALIAS_NAME: String? = null
)

@Serializable
data class UNEntityAlias(
    val QUALITY: String? = null,
    val ALIAS_NAME: String? = null
)

@Serializable
data class UNIndividualAddress(
    val COUNTRY: String? = null,
    val NOTE: String? = null
)

@Serializable
data class UNEntityAddress(
    val STATE_PROVINCE: String? = null,
    val COUNTRY: String? = null
)

@Serializable
data class UNIndividualDateOfBirth(
    val TYPE_OF_DATE: String? = null,
    val DATE: String? = null,
    val YEAR: String? = null
)

@Serializable
data class UNIndividualPlaceOfBirth(
    val COUNTRY: String? = null,
    val CITY: String? = null
)

@Serializable
data class UNIndividualDocument(
    val TYPE_OF_DOCUMENT: String? = null,
    val NUMBER: String? = null,
    val ISSUING_COUNTRY: String? = null
)

@Serializable
data class UNEntityDocument(
    val TYPE_OF_DOCUMENT: String? = null,
    val NUMBER: String? = null,
    val ISSUING_COUNTRY: String? = null
)