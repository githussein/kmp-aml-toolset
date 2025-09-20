package com.example.aml_kyc_tool.SearchScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aml_kyc_tool.data.model.PersonDto
import com.example.amltoolset.data.model.SearchResult
import com.example.amltoolset.data.model.UNEntity
import com.example.amltoolset.data.model.UNIndividual
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
            text = when (state.currentDataSource) {
                DataSource.LOCAL -> "Search UAE Local Terrorist List"
                DataSource.UN -> "Search UN Sanctions List"
                DataSource.ALL -> "Search All Lists (UAE + UN)"
            },
            style = MaterialTheme.typography.titleLarge.copy(
                color = mintDark,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DataSourceButton("UAE Only", DataSource.LOCAL, state.currentDataSource) {
                viewModel.setDataSource(DataSource.LOCAL)
            }
            DataSourceButton("UN Only", DataSource.UN, state.currentDataSource) {
                viewModel.setDataSource(DataSource.UN)
            }
            DataSourceButton("All", DataSource.ALL, state.currentDataSource) {
                viewModel.setDataSource(DataSource.ALL)
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Type a name, entity, or document number") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (state.query.isNotBlank()) {
                    IconButton(onClick = { viewModel.clearSearch() }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            }
        )

        Text(
            text = "Found ${state.results.size} results",
            style = MaterialTheme.typography.bodySmall.copy(color = mintDark),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = mintDark)
            }
        } else if (state.results.isEmpty() && state.query.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No results found", color = mintDark)
            }
        } else if (state.results.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Start typing to search...", color = mintDark)
            }
        } else {
            LazyColumn {
                items(state.results.size) { index ->
                    val result = state.results[index]
                    when (result) {
                        is SearchResult.LocalPersonResult -> LocalPersonCard(result.person)
                        is SearchResult.UNIndividualResult -> UNIndividualCard(result.individual)
                        is SearchResult.UNEntityResult -> UNEntityCard(result.entity)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun DataSourceButton(
    text: String,
    source: DataSource,
    currentSource: DataSource,
    onClick: () -> Unit
) {
    val isSelected = source == currentSource
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) mintDark else Color.White,
            contentColor = if (isSelected) Color.White else mintDark
        ),
        border = BorderStroke(1.dp, mintDark)
    ) {
        Text(text)
    }
}
@Composable
fun LocalPersonCard(person: PersonDto) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // UAE List Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD32F2F)) // UAE red
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "UAE List",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "UAE LOCAL TERRORIST LIST",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Person content (your existing PersonCardModern content)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    person.fullNameArabic,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = mintDark
                )
                Text(
                    person.fullNameLatin,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(Modifier.height(12.dp))

                InfoRow("Classification", person.classification)
                InfoRow("Nationality", person.nationality)
                InfoRow("Date of Birth", person.dateOfBirth)
                InfoRow("Document", "${person.type}: ${person.documentNumber}")
                InfoRow("Additional Info", person.additionalInfo)
            }
        }
    }
}

@Composable
fun UNIndividualCard(individual: UNIndividual) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // UN List Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1976D2)) // UN blue
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = "UN List",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "UN SANCTIONS LIST - INDIVIDUAL",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Individual content
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    buildString {
                        append(individual.FIRST_NAME ?: "")
                        if (!individual.SECOND_NAME.isNullOrBlank()) {
                            append(" ${individual.SECOND_NAME}")
                        }
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = mintDark
                )

                Spacer(Modifier.height(8.dp))

                InfoRow("Reference Number", individual.REFERENCE_NUMBER ?: "N/A")
                InfoRow("Listed On", individual.LISTED_ON ?: "N/A")
                InfoRow("Nationality", individual.NATIONALITY?.VALUE ?: "N/A")
                InfoRow("UN List Type", individual.UN_LIST_TYPE ?: "N/A")

                if (individual.COMMENTS1?.isNotBlank() == true) {
                    InfoRow(
                        "Comments",
                        individual.COMMENTS1.take(100) + if (individual.COMMENTS1.length > 100) "..." else ""
                    )
                }

                // Show aliases if any
                individual.INDIVIDUAL_ALIAS.filter { !it.ALIAS_NAME.isNullOrBlank() }.take(2)
                    .forEach { alias ->
                        InfoRow("Alias", "${alias.ALIAS_NAME} (${alias.QUALITY ?: "unknown"})")
                    }

                // Show documents if any
                individual.INDIVIDUAL_DOCUMENT.take(1).forEach { doc ->
                    InfoRow(
                        "Document",
                        "${doc.TYPE_OF_DOCUMENT ?: "N/A"}: ${doc.NUMBER ?: "N/A"}"
                    )
                }
            }
        }
    }
}

@Composable
fun UNEntityCard(entity: UNEntity) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // UN List Header (Entity)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1976D2)) // UN blue
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = "UN Entity",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "UN SANCTIONS LIST - ENTITY",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Entity content
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    entity.FIRST_NAME ?: "Unnamed Entity",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = mintDark
                )

                Spacer(Modifier.height(8.dp))

                InfoRow("Reference Number", entity.REFERENCE_NUMBER ?: "N/A")
                InfoRow("Listed On", entity.LISTED_ON ?: "N/A")
                InfoRow("UN List Type", entity.UN_LIST_TYPE ?: "N/A")

                if (entity.COMMENTS1?.isNotBlank() == true) {
                    InfoRow(
                        "Comments",
                        entity.COMMENTS1.take(100) + if (entity.COMMENTS1.length > 100) "..." else ""
                    )
                }

                // Show aliases if any
                entity.ENTITY_ALIAS.filter { !it.ALIAS_NAME.isNullOrBlank() }.take(2)
                    .forEach { alias ->
                        InfoRow("Alias", "${alias.ALIAS_NAME} (${alias.QUALITY ?: "unknown"})")
                    }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    if (value.isNotBlank() && value != "N/A") {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "$label: ",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                ),
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
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
