package com.example.epetrol

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.epetrol.screens.FavoritesScreen
import com.example.epetrol.screens.ListScreen
import com.example.epetrol.screens.TempMapScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Map.route
    ) {
        composable(BottomBarScreen.Favorites.route) {
            FavoritesScreen()
        }

        composable(BottomBarScreen.Map.route) {
            TempMapScreen()
        }
        composable(BottomBarScreen.List.route) {
            ListScreen()
        }
    }
}