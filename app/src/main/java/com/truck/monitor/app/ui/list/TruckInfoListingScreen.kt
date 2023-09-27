package com.truck.monitor.app.ui.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.truck.monitor.app.R
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.ui.TruckInfoData
import com.truck.monitor.app.ui.common.TruckInfoCard
import kotlinx.coroutines.launch

@Composable
fun TruckInfoListingScreen(data: TruckInfoData) {
    val truckInfoList = data.list
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.default_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_spacing))
    ) {
        items(
            count = truckInfoList.size,
            key = { truckInfoList[it].plateNo },
            itemContent = {
                val truckInfoItem = truckInfoList[it]
                TruckInfoCard(
                    truckInfoItem = truckInfoItem,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                )
            }
        )
    }
}

@Composable
fun TextX(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}