package com.truck.monitor.app.ui.bottomnavigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.truck.monitor.app.ui.bottomnavigation.NavigationItem

@Composable
fun BottomNavigationHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = NavigationItem.ListItem.route
    ) {
        composable(NavigationItem.ListItem.route) {
            // TODO: Not yet implemented
        }
        composable(NavigationItem.MapItem.route) {
            // TODO: Not yet implemented
        }
    }
}