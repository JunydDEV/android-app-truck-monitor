package com.truck.monitor.app.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.truck.monitor.app.R
import com.truck.monitor.app.ui.TruckInfoData
import com.truck.monitor.app.ui.common.TruckInfoCard
import kotlinx.coroutines.launch

@Composable
fun TruckInfoMapScreen(data: TruckInfoData) {
    Box(
        modifier = Modifier.fillMaxSize(),
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
        SideEffect {
            coroutineScope.launch {
                lazyRowState.animateScrollToItem(0)
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            onMapLoaded = {
                val firstItem = truckInfoList.firstOrNull()
                firstItem?.let {
                    coroutineScope.launch {
                        lazyRowState.animateScrollToItem(0)
                        val selectItemLatLng =
                            LatLng(firstItem.latitude, firstItem.longitude)
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(selectItemLatLng, zoomLevel)
                        markerState.position = selectItemLatLng
                        titleState.value = firstItem.location
                    }
                }
            }
        ) {
            Marker(
                state = markerState,
                title = titleState.value
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
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
                        }
                    }
                )
            }
        }
    }
}