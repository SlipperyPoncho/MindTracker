package com.artem.android.mindtracker.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.artem.android.mindtracker.ui.screens.HomeScreen
import com.artem.android.mindtracker.ui.screens.MoodScreen
import com.artem.android.mindtracker.ui.screens.ProfileScreen

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    HOME("home", "Home", Icons.Default.Home, "Home"),
    MOOD("mood", "Mood", Icons.Default.Face, "Mood"),
    PROFILE("profile", "Profile", Icons.Default.AccountCircle, "Profile")
}

@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        modifier = modifier,
        startDestination = Destination.HOME.route
    ) {
        Destination.entries.forEach { dest ->
            composable(dest.route) {
                when (dest) {
                    Destination.HOME -> HomeScreen()
                    Destination.MOOD -> MoodScreen()
                    Destination.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destination.HOME.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute == dest.route,
                        onClick = {
                            navController.navigate(route = dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                dest.icon,
                                contentDescription = dest.contentDescription
                            )
                        },
                        label = { Text(dest.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        MainNavHost(navController, modifier = Modifier.padding(contentPadding))
    }
}
