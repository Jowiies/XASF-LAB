package edu.upc.xasf.xasf_lab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import edu.upc.xasf.xasf_lab.ui.theme.XASFLABTheme

@Composable
fun App() {
    XASFLABTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RootNavGraph(
                navController = navController,
                paddingValues = innerPadding
            )
        }
    }
}