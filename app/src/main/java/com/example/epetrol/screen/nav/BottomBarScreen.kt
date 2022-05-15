package com.example.epetrol.screen.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Favorites: BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )

    object Map : BottomBarScreen(
        route = "map",
        title = "Map",
        icon = Icons.Default.Map
    )

    object List : BottomBarScreen(
        route = "list",
        title = "List",
        icon = Icons.Default.List
    )
}
