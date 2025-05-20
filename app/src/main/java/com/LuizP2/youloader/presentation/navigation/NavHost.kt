package com.LuizP2.youloader.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.LuizP2.youloader.presentation.ui.screens.DownloadVideoScreen
import com.LuizP2.youloader.presentation.ui.screens.SearchScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = NavRoutes.DOWNLOAD) {
        composable(NavRoutes.DOWNLOAD) {
            DownloadVideoScreen(
                onNavigateToSearchScreen = { navController.navigate(NavRoutes.SEARCH) }
            )
        }
        composable(NavRoutes.SEARCH) {
            SearchScreen(
                searchViewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}
