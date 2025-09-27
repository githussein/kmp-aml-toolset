package com.example.amltoolset.ui.SearchScreen

import com.example.aml_kyc_tool.SearchScreen.SearchViewModel
import com.example.amltoolset.data.SearchService
import com.example.amltoolset.data.model.SearchResult
import com.example.amltoolset.util.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FakeSearchService : SearchService {
    override suspend fun searchAllSources(query: String): List<SearchResult> = emptyList()
    override suspend fun searchLocalOnly(query: String): List<SearchResult> = emptyList()
    override suspend fun searchUNOnly(query: String): List<SearchResult> = emptyList()
}

class SearchViewModelTest {
    private val dispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: SearchViewModel

    @BeforeTest
    fun setup() {
        dispatcherRule.setUp()
        viewModel = SearchViewModel(FakeSearchService())
    }

    @AfterTest
    fun tearDown() {
        dispatcherRule.tearDown()
    }


    @Test
    fun `onQueryChange updates the query in uiState`() {
        val newQuery = "Samy" // arrange
        viewModel.onQueryChange(newQuery) // act
        val updatedUiState = viewModel.uiState.value
        assertEquals(newQuery, updatedUiState.query) //assert
    }
}