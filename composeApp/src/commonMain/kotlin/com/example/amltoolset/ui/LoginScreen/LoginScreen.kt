package com.example.amltoolset.ui.LoginScreen

import amltoolset.composeapp.generated.resources.Res
import amltoolset.composeapp.generated.resources.logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.amltoolset.theme.mintDark
import com.example.amltoolset.theme.mintLight
import com.example.amltoolset.theme.mintPrimary
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModel: LoginViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LoginContent(
        state = state,
        onUsernameChange = viewModel::onUsernameChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginBtnClick = { viewModel.login(onLoginSuccess) }
    )
}

@Composable
private fun LoginContent(
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginBtnClick: () -> Unit
) {
    val mintGradient = Brush.verticalGradient(
        colors = listOf(mintLight, mintPrimary)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mintGradient)
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = state.username,
            onValueChange = { onUsernameChange(it) },
            label = { Text("Username", color = mintDark) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onPasswordChange(it) },
            label = { Text("Password", color = mintDark) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { onLoginBtnClick() },
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = mintDark),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Logging in...",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Text("Login", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
