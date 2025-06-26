package com.artem.android.mindtracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artem.android.mindtracker.ui.viewmodels.AuthState
import com.artem.android.mindtracker.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState = authViewModel.authState.collectAsState()

    LaunchedEffect(key1 = true) {
        authViewModel.authState.collectLatest { state ->
            when (state) {
                is AuthState.Authenticated -> {
                    onAuthSuccess()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = authViewModel.email.value,
            onValueChange = { authViewModel.email.value = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = authViewModel.password.value,
            onValueChange = { authViewModel.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.signIn()
            if (authState.value == AuthState.Authenticated) {
                onAuthSuccess()
            }
        }) {
            Text("Sign in")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.signUp()
            if (authState.value == AuthState.Authenticated) {
                onAuthSuccess()
            }
        }) {
            Text("Sign up")
        }

        when (val state = authState.value) {
            is AuthState.Error -> {
                Text(text = "Error: ${state.msg}")
            }
            AuthState.Loading -> {
                CircularProgressIndicator()
            }
            else -> {}
        }
    }
}
