package com.artem.android.mindtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.artem.android.mindtracker.ui.viewmodels.AuthState
import com.artem.android.mindtracker.ui.viewmodels.AuthViewModel

@Composable
fun RootNavGraph(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState = authViewModel.authState.collectAsState()

    if (authState.value is AuthState.Authenticated) {
        MainNavGraph(navController = navController)
    } else {
        AuthNavGraph(
            navController = navController,
            onAuthSuccess = {
                authViewModel.checkUserAuthenticated()
            }
        )
    }
}
