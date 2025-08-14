package com.example.aml_kyc_tool.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val classification: String,
    val nationality: String,
    val lastNameArabic: String,
    val lastNameLatin: String,
    val fullNameArabic: String,
    val fullNameLatin: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val firstName: String,
    val street: String,
    val city: String,
    val country: String,
    val type: String,
    val documentNumber: String,
    val issuingAuthority: String,
    val issueDate: String,
    val expiryDate: String,
    val additionalInfo: String
)

@Serializable
data class DataListDto(
    val list: String,
    val persons: List<PersonDto>
)