package com.truck.monitor.app.ui.mapview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.truck.monitor.app.R
import com.truck.monitor.app.data.model.TruckMonitoringData
import com.truck.monitor.app.googleMapTestTag
import com.truck.monitor.app.truckInfoHorizontalListTestTag
import com.truck.monitor.app.truckMonitoringMapTestTag
import com.truck.monitor.app.ui.common.TruckInfoCard
import kotlinx.coroutines.launch

@Composable
fun TruckInfoMapScreen(data: TruckMonitoringData) {
    Box(
        modifier = Modifier.fillMaxSize().testTag(truckMonitoringMapTestTag),
        contentAlignment = Alignment.BottomCenter
    ) {
        val truckInfoList = data.list
        val zoomLevel = 15f
        val uaeLatLng = LatLng(23.4241, 53.8478)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(uaeLatLng, zoomLevel)
        }
        val markerState = rememberMarkerState()
        val titleState = remember { mutableStateOf("") }
        val lazyRowState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val visibleItemState =
            rememberSaveable { mutableStateOf(0) }

        GoogleMap(
            modifier = Modifier.fillMaxSize().testTag(googleMapTestTag),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            onMapLoaded = {
                val firstVisibleItem = truckInfoList[visibleItemState.value]
                coroutineScope.launch {
                    lazyRowState.animateScrollToItem(visibleItemState.value)
                    val selectItemLatLng =
                        LatLng(firstVisibleItem.latitude, firstVisibleItem.longitude)
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(selectItemLatLng, zoomLevel)
                    markerState.position = selectItemLatLng
                    titleState.value = firstVisibleItem.location
                }
            }
        ) {
            Marker(
                state = markerState,
                title = titleState.value
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().testTag(truckInfoHorizontalListTestTag),
            state = lazyRowState,
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.default_spacing)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.default_spacing))
        ) {
            items(truckInfoList.size) {
                val truckInfoItem = truckInfoList[it]
                TruckInfoCard(
                    truckInfoItem = truckInfoItem,
                    modifier = Modifier.width(dimensionResource(id = R.dimen.card_width_on_map)),
                    onClick = {
                        coroutineScope.launch {
                            lazyRowState.animateScrollToItem(it)
                            val selectItemLatLng =
                                LatLng(truckInfoItem.latitude, truckInfoItem.longitude)
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(selectItemLatLng, zoomLevel)
                            markerState.position = selectItemLatLng
                            titleState.value = truckInfoItem.location
                            visibleItemState.value = it
                        }
                    }
                )
            }
        }
    }
}