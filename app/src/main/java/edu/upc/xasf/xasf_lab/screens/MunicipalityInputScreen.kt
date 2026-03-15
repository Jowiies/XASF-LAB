package edu.upc.xasf.xasf_lab.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private fun isValidUrl(url: String): Boolean =
    Patterns.WEB_URL.matcher(url.trim()).matches()

@Composable
fun MunicipalityInputScreen(onNavigateToResult: (String) -> Unit) {
    var url by remember { mutableStateOf("https://do.diba.cat/api/dataset/municipis/format/json") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Municipis de Barcelona",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = url,
            onValueChange = {
                url = it
                if (isError) isError = false // Clear error when user types
            },
            label = { Text("URL del servidor") },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (isError) {
                    Text("Si us plau, introdueix una URL vàlida")
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isValidUrl(url)) {
                    onNavigateToResult(url)
                } else {
                    isError = true
                }
            }
        ) {
            Text("Consultar Municipis")
        }
    }
}