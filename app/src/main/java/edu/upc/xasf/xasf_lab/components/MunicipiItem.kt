package edu.upc.xasf.xasf_lab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.upc.xasf.xasf_lab.data.Municipi

@Composable
fun MunicipiItem(municipi: Municipi) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = municipi.nom,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Codi postal: ${municipi.codiPostal}",
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}