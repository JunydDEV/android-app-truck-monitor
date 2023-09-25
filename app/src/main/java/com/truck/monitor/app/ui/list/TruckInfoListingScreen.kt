package com.truck.monitor.app.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.disk.DiskCache
import coil.request.ImageRequest
import com.truck.monitor.app.R
import com.truck.monitor.app.ui.TruckInfoData

@Composable
fun TruckInfoListingScreen(data: TruckInfoData) {
    val list = data.list
    val size = list.size
    val smallSpacing = dimensionResource(id = R.dimen.small_spacing)
    LazyColumn(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.default_spacing)),
        verticalArrangement = Arrangement.spacedBy(smallSpacing)
    ) {
        items(size) {
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(smallSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(smallSpacing)
                ) {
                    val truckInfoItem = list[it]
                    val cacheKey = truckInfoItem.plateNo
                    val imageRequest = ImageRequest.Builder(LocalContext.current)
                        .data(truckInfoItem.image)
                        .crossfade(true)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .diskCacheKey(cacheKey)
                        .build()

                    AsyncImage(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.image_size))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_size))),
                        model = imageRequest,
                        contentScale = ContentScale.Crop,
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