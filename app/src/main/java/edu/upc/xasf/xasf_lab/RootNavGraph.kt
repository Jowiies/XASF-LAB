package edu.upc.xasf.xasf_lab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.upc.xasf.xasf_lab.screens.HomeScreen
import edu.upc.xasf.xasf_lab.screens.SignInScreen
import kotlinx.serialization.Serializable

@Serializable object SignInRoute

@Serializable data class HomeRoute(val email: String, val ipAddress: String)

@Composable
fun RootNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = SignInRoute,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable<SignInRoute> {
            SignInScreen { email, ipAddress ->
                navController.navigate(HomeRoute(email, ipAddress ))
            }
        }

        composable<HomeRoute> { backStackEntry ->
            val args: HomeRoute = backStackEntry.toRoute()
            HomeScreen(
                email = args.email,
                ipAddress = args.ipAddress
            )

        }
    }
}