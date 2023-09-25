package com.truck.monitor.app.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.truck.monitor.app.R
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import com.truck.monitor.app.ui.list.TextX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TruckInfoCard(
    truckInfoItem: TruckInfoListItemDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val smallSpacing = dimensionResource(id = R.dimen.small_spacing)
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(smallSpacing)
        ) {
            val context = LocalContext.current
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .bitmapFactoryMaxParallelism(10)
                .respectCacheHeaders(false)
                .memoryCache {
                    MemoryCache
                        .Builder(context)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .build()
                }
                .build()

            val imageRequest = ImageRequest.Builder(context)
                .data(truckInfoItem.image)
                .crossfade(true)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .allowHardware(true)
                .diskCacheKey(truckInfoItem.image)
                .memoryCacheKey(truckInfoItem.image)
                .build()

            AsyncImage(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.image_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_size))),
                model = imageRequest,
                contentScale = ContentScale.Crop,
                imageLoader = imageLoader,
                contentDescription = "driver image",
            )
            Column(verticalArrangement = Arrangement.spacedBy(smallSpacing)) {
                TextX(label = "Plate No", value = truckInfoItem.plateNo)
                TextX(label = "Driver Name", value = truckInfoItem.driverName)
                TextX(label = "Location", value = truckInfoItem.address)
                TextX(label = "Last Updated", value = truckInfoItem.lastUpdate)
            }
        }

    }
}