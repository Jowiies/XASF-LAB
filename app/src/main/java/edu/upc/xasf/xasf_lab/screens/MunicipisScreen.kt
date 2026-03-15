package edu.upc.xasf.xasf_lab.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.upc.xasf.xasf_lab.data.Municipi
import edu.upc.xasf.xasf_lab.network.MunicipisRepository
import edu.upc.xasf.xasf_lab.components.MunicipiItem

@Composable
fun MunicipisScreen() {
    val context = LocalContext.current
    val repository = remember { MunicipisRepository(context) }

    var municipis by remember { mutableStateOf<List<Municipi>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }

    val url = "https://do.diba.cat/api/dataset/municipis/format/json"

    fun carregarMunicipis() {
        loading = true

        repository.obtenirMunicipis(
            url = url,
            onSuccess = { llista ->
                municipis = llista
                loading = false
            },
            onError = { error ->
                loading = false
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        )
    }

    LaunchedEffect(Unit) {
        carregarMunicipis()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Municipis",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { carregarMunicipis() },
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
        ) {
            Text("Recarregar")
        }

        if (loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(municipis) { municipi ->
                    MunicipiItem(municipi = municipi)
                }
            }
        }
    }
}