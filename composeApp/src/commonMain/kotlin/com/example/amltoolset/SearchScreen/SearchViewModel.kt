package com.example.aml_kyc_tool.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amltoolset.data.SearchService
import com.example.amltoolset.data.model.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchService: SearchService
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var currentDataSource: DataSource = DataSource.UN

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        search()
    }

    fun setDataSource(source: DataSource) {
        currentDataSource = source
        search()
    }

    private fun search() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val results = when (currentDataSource) {
                DataSource.LOCAL -> searchService.searchLocalOnly(_uiState.value.query)
                DataSource.UN -> searchService.searchUNOnly(_uiState.value.query)
                DataSource.ALL -> searchService.searchAllSources(_uiState.value.query)
            }

            _uiState.update {
                it.copy(
                    results = results,
                    isLoading = false,
                    currentDataSource = currentDataSource
                )
            }
        }
    }

    fun clearSearch() {
        _uiState.update {
            it.copy(
                query = "",
                results = emptyList(),
                isLoading = false
            )
        }
    }
}

enum class DataSource {
    LOCAL, UN, ALL
}

data class SearchUiState(
    val query: String = "",
    val results: List<SearchResult> = emptyList(),
    val isLoading: Boolean = false,
    val currentDataSource: DataSource = DataSource.UN
)