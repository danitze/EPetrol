package com.example.epetrol.screens.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.epetrol.screens.FavouritesScreen
import com.example.epetrol.screens.ListScreen
import com.example.epetrol.screens.TempMapScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(BottomBarScreen.Favorites.route) {
            FavouritesScreen()
        }

        composable(BottomBarScreen.Map.route) {
            TempMapScreen()
        }
        composable(BottomBarScreen.List.route) {
            ListScreen()
        }
    }
}