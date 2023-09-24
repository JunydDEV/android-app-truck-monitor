package com.truck.monitor.app.ui.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navigationItems = listOf(
        NavigationItem.ListItem,
        NavigationItem.MapItem
    )
    val selectedItemState = remember {
        mutableStateOf(0)
    }
    val currentRouteState = remember {
        mutableStateOf(NavigationItem.ListItem.route)
    }

    navigationItems.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRouteState.value) {
            selectedItemState.value = index
        }
    }

    NavigationBar {
        navigationItems.forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(navigationItem.icon, contentDescription = "Navigation item") },
                selected = selectedItemState.value == index,
                label = { Text(text = navigationItem.title) },
                onClick = {
                    selectedItemState.value = index
                    currentRouteState.value = navigationItem.route
                    navController.navigate(navigationItem.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

sealed class NavigationItem(var route: String, val icon: ImageVector, var title: String) {
    object ListItem : NavigationItem("List", Icons.Rounded.List, "List")
    object MapItem : NavigationItem("Map", Icons.Rounded.LocationOn, "Map")
}
