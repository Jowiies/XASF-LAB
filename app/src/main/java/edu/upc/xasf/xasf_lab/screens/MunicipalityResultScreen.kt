package edu.upc.xasf.xasf_lab.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.upc.xasf.xasf_lab.viewmodel.MunicipalityUiState
import edu.upc.xasf.xasf_lab.viewmodel.MunicipalityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MunicipalityResultScreen(
    targetUrl: String,
    onBackClick: () -> Unit,
    viewModel: MunicipalityViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(targetUrl) {
        viewModel.fetchList(targetUrl)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Municipis de Barcelona") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is MunicipalityUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is MunicipalityUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.data) { municipality ->
                            ListItem(
                                headlineContent = { Text(municipality.name) },
                                supportingContent = {
                                    Text("Codi Postal: ${municipality.info.postalCode}")
                                },
                                leadingContent = {
                                    Icon(Icons.Default.LocationOn, contentDescription = null)
                                }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }

                is MunicipalityUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌", style = MaterialTheme.typography.displayMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.fetchList(targetUrl) }) {
                            Text("Reintentar")
                        }
                    }
                }

                else -> {} // Idle state
            }
        }
    }
}