package com.example.epetrol.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.epetrol.screen.MainScreen
import com.example.epetrol.screen.SignInScreen
import com.example.epetrol.screen.SignUpScreen

@Composable
fun MainNavGraph(baseUrl: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthScreen.SignIn.route) {
        composable(AuthScreen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable(AuthScreen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(AuthScreen.Main.route) {
            MainScreen(baseUrl = baseUrl)
        }
    }
}