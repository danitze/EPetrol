package com.example.epetrol.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.epetrol.screen.FavouritesScreen
import com.example.epetrol.screen.ListScreen
import com.example.epetrol.screen.MapScreen
import com.example.epetrol.viewmodel.MainViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    baseUrl: String,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(BottomBarScreen.Favorites.route) {
            FavouritesScreen(baseUrl = baseUrl, mainViewModel = mainViewModel)
        }

        composable(BottomBarScreen.Map.route) {
            MapScreen()
        }
        composable(BottomBarScreen.List.route) {
            ListScreen(baseUrl = baseUrl, mainViewModel = mainViewModel)
        }
    }
}