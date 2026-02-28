package edu.upc.xasf.xasf_lab.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SignInScreen(onLoginClick: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var ipAddress by remember { mutableStateOf("") }

    Column {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        TextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("IP Address") }
        )

        Button(onClick = { onLoginClick(username, ipAddress) }) {
            Text("Sign In")
        }
    }
}