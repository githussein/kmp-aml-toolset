package com.example.aml_kyc_tool.SearchScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aml_kyc_tool.data.PersonRepository
import com.example.aml_kyc_tool.data.model.PersonDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val personRepository: PersonRepository?
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        search()
    }

    private fun search() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val persons = personRepository?.getPersons()
            val results = if (_uiState.value.query.isBlank()) {
                emptyList()
            } else {
                persons?.filter {
                    it.toString().contains(_uiState.value.query, ignoreCase = true)
                }
            }

            _uiState.update { it.copy(results = results ?: emptyList(), isLoading = false) }
        }
    }
}

data class SearchUiState(
    val query: String = "",
    val results: List<PersonDto> = emptyList(),
    val isLoading: Boolean = false
)