package edu.upc.xasf.xasf_lab.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.upc.xasf.xasf_lab.utils.NetworkUtils
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

    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val wifiInfo = remember(hasLocationPermission) {
        NetworkUtils.getWifiDetails(context)
    }

    LaunchedEffect(targetUrl) {
        viewModel.fetchList(context, targetUrl)
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
                    Column(modifier = Modifier.fillMaxSize()) {

                        // 4. Show the WiFi details at the top of the list
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Info, contentDescription = "WiFi")
                                Spacer(Modifier.width(16.dp))
                                Text(wifiInfo, style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        // The original list of municipalities
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
                }

                is MunicipalityUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌", style = MaterialTheme.typography.displayMedium)
                        Spacer(Modifier.height(8.dp))
                        // The ViewModel already sends the WiFi error message here!
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(16.dp))

                        // 5. Pass the context to the retry button as well
                        Button(onClick = { viewModel.fetchList(context, targetUrl) }) {
                            Text("Reintentar")
                        }
                    }
                }

                else -> {} // Idle state
            }
        }
    }
}