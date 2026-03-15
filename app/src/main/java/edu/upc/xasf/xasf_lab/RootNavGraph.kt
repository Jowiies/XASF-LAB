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
import edu.upc.xasf.xasf_lab.screens.MunicipalityInputScreen
import edu.upc.xasf.xasf_lab.screens.MunicipalityResultScreen
import edu.upc.xasf.xasf_lab.screens.SignInScreen
import kotlinx.serialization.Serializable

@Serializable object SignInRoute

@Serializable data class HomeRoute(val email: String, val ipAddress: String)
@Serializable data class MunicipalityResultRoute(val url: String)
@Serializable object MunicipalityInputRoute

@Composable
fun RootNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = MunicipalityInputRoute,
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

        composable<MunicipalityInputRoute> {
           MunicipalityInputScreen(onNavigateToResult = { url ->
               navController.navigate(MunicipalityResultRoute(url=url))
           })
        }

        composable<MunicipalityResultRoute> { backStackEntry ->
            val args: MunicipalityResultRoute = backStackEntry.toRoute()
            MunicipalityResultScreen(
                targetUrl = args.url,
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}