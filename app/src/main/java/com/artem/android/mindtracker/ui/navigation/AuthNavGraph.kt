package com.artem.android.mindtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.artem.android.mindtracker.ui.screens.AuthScreen

enum class AuthDestination(val route: String) {
    AUTH("auth")
}

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AuthDestination.AUTH.route
    ) {
        composable(route = AuthDestination.AUTH.route) {
            AuthScreen(onAuthSuccess = onAuthSuccess)
        }
    }
}
