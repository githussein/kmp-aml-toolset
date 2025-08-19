package com.example.aml_kyc_tool.SearchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aml_kyc_tool.data.model.PersonDto
import com.example.amltoolset.theme.mintDark
import org.koin.compose.koinInject


@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = koinInject()
    val state by viewModel.uiState.collectAsState()

    val mintPrimary = Color(0xFF3EB489)
    val mintLight = Color(0xFFC8FACC)
    val mintDark = Color(0xFF2A7F66)
    val mintGradient = Brush.verticalGradient(
        colors = listOf(mintLight, mintPrimary)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mintGradient)
            .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 20.dp)
    ) {

        Text(
            text = "Search UAE Local Terrorist List",
            style = MaterialTheme.typography.titleLarge.copy(
                color = mintDark,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Person or entity name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.results.isEmpty()) {
            Text("No results")
        } else {
            LazyColumn {
                items(state.results.size) { index ->
                    val person = state.results[index]
                    PersonCardModern(person)
                }
            }
        }
    }
}

@Composable
fun PersonCardModern(person: PersonDto) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red.copy(alpha = 0.7f))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        person.fullNameArabic,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        person.fullNameLatin,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    )
                }
            }


            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow("Classification", person.classification)
                InfoRow("Nationality", person.nationality)
                InfoRow("Date of Birth", person.dateOfBirth)
                InfoRow("Place of Birth", person.placeOfBirth)
                InfoRow("Document Type", person.type)
                InfoRow("Document Number", person.documentNumber)
                InfoRow("Issuing Authority", person.issuingAuthority)
                InfoRow("Additional Info", person.additionalInfo)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall.copy(color = mintDark.copy(alpha = 0.8f))
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = mintDark,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 2
        )
    }
}