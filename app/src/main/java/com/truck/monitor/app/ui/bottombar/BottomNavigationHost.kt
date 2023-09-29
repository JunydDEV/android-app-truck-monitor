package com.truck.monitor.app.ui.bottombar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.truck.monitor.app.data.model.TruckMonitoringData
import com.truck.monitor.app.ui.listview.TruckInfoListingScreen
import com.truck.monitor.app.ui.mapview.TruckInfoMapScreen

@Composable
fun BottomNavigationHost(navController: NavHostController, truckMonitoringData: TruckMonitoringData) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = NavigationItem.ListItem.route
    ) {
        composable(NavigationItem.ListItem.route) {
            TruckInfoListingScreen(data = truckMonitoringData)
        }
        composable(NavigationItem.MapItem.route) {
            TruckInfoMapScreen(data = truckMonitoringData)
        }
    }
}