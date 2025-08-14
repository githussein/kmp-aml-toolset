package com.example.amltoolset

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aml_kyc_tool.SearchScreen.SearchScreen
import com.example.aml_kyc_tool.SearchScreen.SearchViewModel
import com.example.aml_kyc_tool.data.LocalJsonPersonRepository
import com.example.amltoolset.LoginScreen.LoginScreen
import com.example.amltoolset.LoginScreen.LoginViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val repository = LocalJsonPersonRepository()
        val searchViewModel = SearchViewModel(repository)
        val loginViewModel = LoginViewModel()


        NavHost(navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate("search")
                    })
            }

            composable("search") {
                SearchScreen(viewModel = searchViewModel)
            }
        }
    }
}
