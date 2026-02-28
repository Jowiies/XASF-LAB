package edu.upc.xasf.xasf_lab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.upc.xasf.xasf_lab.screens.SignInScreen
import kotlinx.serialization.Serializable

@Serializable object SignInScreen

@Serializable data class Home(val userName: String, val ipAddress: String)

@Composable
fun RootNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = SignInScreen,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable<SignInScreen> {
        }

        composable<Home> { backStackEntry ->
            val homeRoute: Home = backStackEntry.toRoute()

        }
    }
}