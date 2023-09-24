package com.truck.monitor.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.truck.monitor.app.R
import com.truck.monitor.app.ui.theme.TruckMonitorAppTheme

@Composable
fun TrucksMonitoringApp() {
    TruckMonitorAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = { MainScreen() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { AppTopBar() },
        content = { paddingValues -> MainScreenContent(paddingValues = paddingValues) }
    )
}

@Preview(showBackground = false)
@Composable
fun MainScreenPreview() {
    TruckMonitorAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = { MainScreen() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = { AppTitle() },
        actions = { SortListingAction() },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun AppTitle() {
    Text(
        text = "Truck Monitor",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun SortListingAction() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sort),
            contentDescription = "sorting icon",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun MainScreenContent(paddingValues: PaddingValues, modifier: Modifier = Modifier) {
    val searchFieldState = remember { mutableStateOf("") }
    Column(modifier = modifier
        .padding(paddingValues)
        .fillMaxSize()) {

        SearchTextField(
            value = searchFieldState.value,
            onValueChange = { searchFieldState.value = it }
        )

        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp)) {

        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = "Search")},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomNavigationBar() {
    // TODO("Not yet implemented")
}

