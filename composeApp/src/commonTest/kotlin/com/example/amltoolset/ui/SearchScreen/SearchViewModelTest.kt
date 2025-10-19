package com.example.amltoolset.ui.SearchScreen

import com.example.aml_kyc_tool.SearchScreen.SearchViewModel
import com.example.amltoolset.data.SearchService
import com.example.amltoolset.util.MainDispatcherRule
import io.mockative.coEvery
import io.mockative.coVerify
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import io.mockative.mock
import io.mockative.of

class SearchViewModelTest {
    private val dispatcherRule = MainDispatcherRule()

    val mockSearchService = mock(of<SearchService>())

    private lateinit var viewModel: SearchViewModel

    @BeforeTest
    fun setup() {
        dispatcherRule.setUp()
        viewModel = SearchViewModel(mockSearchService)
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
        assertEquals(newQuery, updatedUiState.query) // assert
    }


    @Test
    fun `onQueryChange triggers search service`() = runTest {
        // arrange
        val query = "test query"
        coEvery { mockSearchService.searchAllSources(query) }
            .returns(emptyList())
        // act
        viewModel.onQueryChange(query)
        // assert
        coVerify { mockSearchService.searchAllSources(query) }.wasInvoked()
    }
}