package edu.upc.xasf.xasf_lab.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment

private fun isValidEmail(email: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

private fun isValidIP(ip: String): Boolean =
    Patterns.IP_ADDRESS.matcher(ip.trim()).matches()




@Composable
fun SignInScreen(onLoginClick: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var ipAddress by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isIpError by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = isEmailError,
            supportingText = {
                if (isEmailError) {
                    Text("Email invalid")
                }
            }
        )

        TextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("IP Address") },
            isError = isIpError,
            supportingText = {
                if(isIpError) {
                    Text("IP invalid")
                }
            }
        )

        Button(onClick = {
            isEmailError = !isValidEmail(email)
            isIpError = !isValidIP(ipAddress)

            if (!isEmailError && !isIpError) {
                onLoginClick(email, ipAddress)
            }
        }) {
            Text("Sign In")
        }
    }
}