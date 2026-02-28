package edu.upc.xasf.xasf_lab

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import edu.upc.xasf.xasf_lab.ui.theme.XASFLABTheme

@Composable
fun App() {
    XASFLABTheme{
        val navController = rememberNavController()
        Scaffold() {innerPadding ->
            RootNavGraph(
                navController = navController,
                paddingValues =  innerPadding
            )
        }
    }
}