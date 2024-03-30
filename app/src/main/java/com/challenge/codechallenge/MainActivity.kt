package com.challenge.codechallenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.challenge.codechallenge.ui.LoginState
import com.challenge.codechallenge.ui.MainViewModel
import com.challenge.codechallenge.ui.theme.CodeChallengeTheme

// Create an Android app that simulates a login process using Kotlin coroutines. The app should have the following features:

// Login form: Design a simple login form with fields for username and password. Login Button
// Simulated authentication: Utilize Kotlin coroutines to simulate an authentication process. For example, you can use a coroutine with a delay to mimic the authentication process taking some time.
// Display login result: After the simulated authentication, display a message indicating whether the login was successful or not using a toast.
// Error handling: Handle cases where there are invalid credentials gracefully, displaying appropriate error messages to the user.
// Loading indicator: Show a loading indicator while the authentication process is ongoing.

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeChallengeTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val viewModel: MainViewModel = viewModel()

    LoginContent(viewModel = viewModel)
}

@Composable
fun LoginContent(viewModel: MainViewModel) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = user,
            onValueChange = { value ->
                user = value
            },
            label = {
                Text(text = "User")
            },
            maxLines = 1,
        )

        TextField(
            value = password,
            onValueChange = { value ->
                password = value
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
        )

        if (loginState is LoginState.Loadingg) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                viewModel.login(
                    user = user,
                    password = password,
                )
            }, enabled = loginState !is LoginState.Loadingg) {
                Text(text = "Login")
            }
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show()
            }
            is LoginState.Error -> {
                val error = (loginState as LoginState.Error).error
                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodeChallengeTheme {
        LoginContent(viewModel = MainViewModel())
    }
}