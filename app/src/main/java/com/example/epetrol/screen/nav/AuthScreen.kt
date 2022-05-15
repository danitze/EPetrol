package com.example.epetrol.screen.nav

sealed class AuthScreen(
    val route: String
) {
    object SignIn: AuthScreen("signIn")
    object SignUp: AuthScreen("signUp")
    object Main: AuthScreen("mainScreen")
}
