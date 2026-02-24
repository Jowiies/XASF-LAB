package edu.upc.xasf.xasf_lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.upc.xasf.xasf_lab.ui.theme.XASFLABTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XASFLABTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                Column(
                ) {
                    UserInputScreen(placeholder = "email")
                    UserInputScreen(placeholder = "ip address")
                    AppButton(
                        text = "Jose Ramón",
                        onClick = {}
                    )
                }


            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun AppButton(onClick: () -> Unit, text: String){
    Button(onClick = {onClick() }) {
        Text(text=text)
    }
}

@Composable
fun UserInputScreen(placeholder: String) {
    var userInput by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            value = userInput,
            onValueChange = { newText ->
                userInput = newText
            },
            placeholder = {Text(text = placeholder)}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Column(
    ) {
        UserInputScreen(placeholder = "email")
        UserInputScreen(placeholder = "ip address")
        AppButton(
            text = "Jose Ramón",
            onClick = {}
        )
    }
}