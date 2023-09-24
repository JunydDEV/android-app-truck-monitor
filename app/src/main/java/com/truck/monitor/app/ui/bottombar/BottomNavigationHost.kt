package com.truck.monitor.app.ui.bottombar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.truck.monitor.app.ui.TruckInfoData
import com.truck.monitor.app.ui.list.TruckInfoListingScreen

@Composable
fun BottomNavigationHost(navController: NavHostController, truckInfoData: TruckInfoData) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = NavigationItem.ListItem.route
    ) {
        composable(NavigationItem.ListItem.route) {
            TruckInfoListingScreen(data = truckInfoData)
        }
        composable(NavigationItem.MapItem.route) {
            // TODO: Not yet implemented
        }
    }
}