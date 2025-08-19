package com.example.amltoolset.data.model

import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import kotlinx.serialization.Serializable
@Serializable
@XmlSerialName("CONSOLIDATED_LIST")
data class UNConsolidatedList(
    @XmlElement(true)
    val INDIVIDUALS: UNIndividuals? = null,

    @XmlElement(true)
    val ENTITIES: UNEntities? = null
)

@Serializable
@XmlSerialName("INDIVIDUALS")
data class UNIndividuals(
    @XmlElement(true)
    val INDIVIDUAL: List<UNIndividual> = emptyList()
)

@Serializable
@XmlSerialName("ENTITIES")
data class UNEntities(
    @XmlElement(true)
    val ENTITY: List<UNEntity> = emptyList()
)

@Serializable
@XmlSerialName("INDIVIDUAL")
data class UNIndividual(
    @XmlElement(true)
    val DATAID: String? = null,

    @XmlElement(true)
    val FIRS_NAME: String? = null,

    @XmlElement(true)
    val SECOND_NAME: String? = null,

    @XmlElement(true)
    val UN_LIST_TYPE: String? = null,

    @XmlElement(true)
    val REFERENCE_NUMBER: String? = null,

    @XmlElement(true)
    val LISTED_ON: String? = null,

    @XmlElement(true)
    val GENDER: String? = null,

    @XmlElement(true)
    val COMMENTS1: String? = null,

    @XmlElement(true)
    val NATIONALITY: UNNationality? = null,

    @XmlElement(true)
    val LIST_TYPE: UNListType? = null,

    @XmlElement(true)
    val LAST_DAY_UPDATED: UNLastDayUpdated? = null,

    @XmlElement(true)
    val INDIVIDUAL_ALIAS: List<UNIndividualAlias> = emptyList(),

    @XmlElement(true)
    val INDIVIDUAL_ADDRESS: List<UNIndividualAddress> = emptyList(),

    @XmlElement(true)
    val INDIVIDUAL_DATE_OF_BIRTH: List<UNIndividualDateOfBirth> = emptyList(),

    @XmlElement(true)
    val INDIVIDUAL_PLACE_OF_BIRTH: List<UNIndividualPlaceOfBirth> = emptyList(),

    @XmlElement(true)
    val INDIVIDUAL_DOCUMENT: List<UNIndividualDocument> = emptyList(),

    @XmlElement(true)
    val SORT_KEY: String? = null,

    @XmlElement(true)
    val SORT_KEY_LAST_MOD: String? = null
)

@Serializable
@XmlSerialName("ENTITY")
data class UNEntity(
    @XmlElement(true)
    val DATAID: String? = null,

    @XmlElement(true)
    val FIRST_NAME: String? = null,

    @XmlElement(true)
    val UN_LIST_TYPE: String? = null,

    @XmlElement(true)
    val REFERENCE_NUMBER: String? = null,

    @XmlElement(true)
    val LISTED_ON: String? = null,

    @XmlElement(true)
    val COMMENTS1: String? = null,

    @XmlElement(true)
    val LIST_TYPE: UNListType? = null,

    @XmlElement(true)
    val LAST_DAY_UPDATED: UNLastDayUpdated? = null,

    @XmlElement(true)
    val ENTITY_ALIAS: List<UNEntityAlias> = emptyList(),

    @XmlElement(true)
    val ENTITY_ADDRESS: List<UNEntityAddress> = emptyList(),

    @XmlElement(true)
    val SORT_KEY: String? = null,

    @XmlElement(true)
    val SORT_KEY_LAST_MOD: String? = null
)

@Serializable
data class UNNationality(
    @XmlElement(true)
    val VALUE: String? = null
)

@Serializable
data class UNListType(
    @XmlElement(true)
    val VALUE: String? = null
)

@Serializable
data class UNLastDayUpdated(
    @XmlElement(true)
    val VALUE: List<String> = emptyList()
)

@Serializable
@XmlSerialName("INDIVIDUAL_ALIAS")
data class UNIndividualAlias(
    @XmlElement(true)
    val QUALITY: String? = null,

    @XmlElement(true)
    val ALIAS_NAME: String? = null
)

@Serializable
@XmlSerialName("ENTITY_ALIAS")
data class UNEntityAlias(
    @XmlElement(true)
    val QUALITY: String? = null,

    @XmlElement(true)
    val ALIAS_NAME: String? = null
)

@Serializable
@XmlSerialName("INDIVIDUAL_ADDRESS")
data class UNIndividualAddress(
    @XmlElement(true)
    val COUNTRY: String? = null,

    @XmlElement(true)
    val NOTE: String? = null
)

@Serializable
@XmlSerialName("ENTITY_ADDRESS")
data class UNEntityAddress(
    @XmlElement(true)
    val STATE_PROVINCE: String? = null,

    @XmlElement(true)
    val COUNTRY: String? = null
)

@Serializable
@XmlSerialName("INDIVIDUAL_DATE_OF_BIRTH")
data class UNIndividualDateOfBirth(
    @XmlElement(true)
    val TYPE_OF_DATE: String? = null,

    @XmlElement(true)
    val DATE: String? = null,

    @XmlElement(true)
    val YEAR: String? = null
)

@Serializable
@XmlSerialName("INDIVIDUAL_PLACE_OF_BIRTH")
data class UNIndividualPlaceOfBirth(
    @XmlElement(true)
    val COUNTRY: String? = null,

    @XmlElement(true)
    val CITY: String? = null
)

@Serializable
@XmlSerialName("INDIVIDUAL_DOCUMENT")
data class UNIndividualDocument(
    @XmlElement(true)
    val TYPE_OF_DOCUMENT: String? = null,

    @XmlElement(true)
    val NUMBER: String? = null,

    @XmlElement(true)
    val ISSUING_COUNTRY: String? = null
)

@Serializable
@XmlSerialName("ENTITY_DOCUMENT")
data class UNEntityDocument(
    @XmlElement(true)
    val TYPE_OF_DOCUMENT: String? = null,

    @XmlElement(true)
    val NUMBER: String? = null,

    @XmlElement(true)
    val ISSUING_COUNTRY: String? = null
)