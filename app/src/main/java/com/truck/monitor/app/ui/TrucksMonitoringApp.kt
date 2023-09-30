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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.truck.monitor.app.R
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import com.truck.monitor.app.data.model.TruckMonitoringData
import com.truck.monitor.app.ui.bottombar.BottomNavigationBar
import com.truck.monitor.app.ui.bottombar.BottomNavigationHost
import com.truck.monitor.app.ui.common.FailureScreen
import com.truck.monitor.app.ui.common.ProgressIndicator
import com.truck.monitor.app.ui.state.UiState
import com.truck.monitor.app.ui.theme.TruckMonitorAppTheme

@Composable
fun TrucksMonitoringApp() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    val truckInfoState = viewModel.truckMonitoringStateFlow.collectAsState()
    val sortingOrderState = remember { mutableStateOf<SortingOrder?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchTruckMonitoringData()
    }
    LaunchedEffect(key1 = sortingOrderState.value) {
        sortingOrderState.value?.let {
            viewModel.sortTruckMonitoringData(it)
        }
    }

    TruckMonitorAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = {
                MainScreen(
                    viewModel = viewModel,
                    navController = navController,
                    truckInfoState = truckInfoState,
                    sortOrderValue = sortingOrderState.value,
                    onSortOrderValueChange = { sortingOrderState.value = it }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    truckInfoState: State<UiState>,
    sortOrderValue: SortingOrder?,
    onSortOrderValueChange:(SortingOrder) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                sortOrderValue = sortOrderValue,
                onSortOrderValueChange = onSortOrderValueChange
            )
        },
        content = { paddingValues ->
            MainScreenContent(
                viewModel = viewModel,
                navController = navController,
                paddingValues = paddingValues,
                truckInfoState = truckInfoState
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    sortOrderValue: SortingOrder?,
    onSortOrderValueChange: (SortingOrder) -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { AppTitle() },
        actions = { SortListingAction(sortOrderValue, onSortOrderValueChange) },
        modifier = modifier
            .fillMaxWidth()
            .testTag(topAppbarTestTag),
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
fun SortListingAction(
    sortOrderValue: SortingOrder?,
    onSortOrderValueChange: (SortingOrder) -> Unit,
) {
    IconButton(
        modifier = Modifier.testTag(sortListingTestTag),
        onClick = {
        onSortOrderValueChange(
            if (sortOrderValue == SortingOrder.ASC) SortingOrder.DESC
            else SortingOrder.ASC
        )
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sort),
            contentDescription = "sorting icon",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun MainScreenContent(
    viewModel: MainViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    truckInfoState: State<UiState>
) {
    val searchFieldState = remember { mutableStateOf("") }
    LaunchedEffect(key1 = searchFieldState.value) {
        viewModel.searchTruckMonitoringData(searchFieldState.value)
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        SearchTextField(
            value = searchFieldState.value,
            onValueChange = { searchFieldState.value = it },
        )

        when (val uiState = truckInfoState.value) {
            is UiState.OnLoading -> {
                ProgressIndicator()
            }

            is UiState.OnFailure -> {
                FailureScreen(uiState)
            }

            is UiState.OnSuccess<*> -> {
                val truckInfoList = uiState.data as List<TruckInfoListItemDto>
                BottomNavigationHost(
                    navController = navController,
                    truckMonitoringData = TruckMonitoringData(truckInfoList)
                )
            }
            else -> {}
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(dimensionResource(id = R.dimen.default_spacing))
    ) {
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(searchFieldTestTag),
            singleLine = true
        )
    }
}
