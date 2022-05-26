package com.example.epetrol.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.epetrol.intent.MainIntent
import com.example.epetrol.result.SignOutResult
import com.example.epetrol.screen.nav.AuthScreen
import com.example.epetrol.screen.nav.BottomBarScreen
import com.example.epetrol.screen.nav.BottomNavGraph
import com.example.epetrol.viewmodel.MainViewModel

@Composable
fun MainScreen(
    authNavController: NavController,
    baseUrl: String,
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(navController = authNavController) },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(navController = navController, baseUrl = baseUrl)
        }
    }
}

@Composable
fun TopBar(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.signOutResultsFlow.collect { result ->
            when(result) {
                is SignOutResult.Error -> Toast.makeText(
                    context,
                    result.msg,
                    Toast.LENGTH_LONG
                ).show()
                is SignOutResult.Unauthorized -> {
                    navController.navigate(AuthScreen.SignIn.route) {
                        popUpTo(AuthScreen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        IconButton(onClick = { viewModel.onIntent(MainIntent.SignOut) }) {
            Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "exit")
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Favorites,
        BottomBarScreen.Map,
        BottomBarScreen.List
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = screen.title) },
        icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation icon") },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } ?: false,
        onClick = {
            navController.navigate(route = screen.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        },
    )
}