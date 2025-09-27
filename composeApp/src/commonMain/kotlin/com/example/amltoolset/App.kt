package com.example.amltoolset

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aml_kyc_tool.SearchScreen.SearchScreen
import com.example.amltoolset.ui.LoginScreen.LoginScreen
import com.example.amltoolset.di.initKoin

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { navController.navigate("search") })
            }

            composable("search") {
                SearchScreen()
            }
        }
    }
}
