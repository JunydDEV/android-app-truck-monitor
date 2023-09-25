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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.truck.monitor.app.R
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import com.truck.monitor.app.ui.list.TextX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TruckInfoCard(
    truckInfoItem: TruckInfoListItemDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
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