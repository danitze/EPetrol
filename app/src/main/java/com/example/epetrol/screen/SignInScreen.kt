package com.example.epetrol.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.epetrol.intent.SignInIntent
import com.example.epetrol.screen.nav.AuthScreen
import com.example.epetrol.viewmodel.SignInViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = hiltViewModel()) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val state = viewModel.state

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = state.signInEmail,
            onValueChange = {
                viewModel.onIntent(SignInIntent.SignInEmailChanged(it))
            },
            label = { Text(text = "Enter email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.padding(bottom = 5.dp),
        )
        OutlinedTextField(
            value = state.signInPassword,
            onValueChange = {
                viewModel.onIntent(SignInIntent.SignInPasswordChanged(it))
            },
            label = { Text(text = "Enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            modifier = Modifier.padding(bottom = 5.dp),
        )
        Button(onClick = {
            navController.navigate(AuthScreen.Main.route) {
                keyboardController?.hide()
                popUpTo(AuthScreen.SignIn.route) {
                    inclusive = true
                }
            }
        }) {
            Text(text = "Sign in")
        }

        Text(
            text = "Not signed up yet?", modifier = Modifier.clickable {
                navController.navigate(AuthScreen.SignUp.route) {
                    popUpTo(AuthScreen.SignIn.route)
                }
            },
            color = MaterialTheme.colors.secondary
        )
    }
}