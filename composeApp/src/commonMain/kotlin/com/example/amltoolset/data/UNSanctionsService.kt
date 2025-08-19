package com.example.amltoolset.data

import com.example.amltoolset.data.model.UNConsolidatedList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface UNSanctionsService {
    suspend fun getUNSanctionsList(): UNConsolidatedList
}

class UNSanctionsServiceImpl(private val client: HttpClient) : UNSanctionsService {
    override suspend fun getUNSanctionsList(): UNConsolidatedList {
        return client.get("https://scsanctions.un.org/resources/xml/en/consolidated.xml").body()
    }
}