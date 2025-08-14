package com.example.amltoolset.LoginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onUsernameChanged(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun login(onSuccess: () -> Unit) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = "") }

        delay(1500) // temporarily simulate login

        _state.update {
            it.copy(
                isLoading = false,
                //fixme when authentication service is ready
                errorMessage = if (/*it.username == "admin" && it.password == "dubai123"*/ true) {
                    onSuccess()
                    ""
                } else {
                    "Invalid username or password"
                }
            )
        }
    }
}

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)