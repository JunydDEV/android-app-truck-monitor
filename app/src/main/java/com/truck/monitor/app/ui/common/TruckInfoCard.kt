package com.truck.monitor.app.ui.common

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.dimensionResource
import coil.compose.SubcomposeAsyncImage
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
        modifier = modifier.height(dimensionResource(id = R.dimen.card_normal_height)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
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
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.image_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_size))),
                model = truckInfoItem.image,
                loading = {
                    Spacer(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_size)))
                    )
                },
                error = {
                    Spacer(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_size)))
                    )
                },
                contentScale = ContentScale.Crop,
                contentDescription = "driver image",
            )
            Column(verticalArrangement = Arrangement.spacedBy(smallSpacing)) {
                TextX(label = "Plate No", value = truckInfoItem.plateNo)
                TextX(label = "Driver Name", value = truckInfoItem.driverName)
                TextX(label = "Location", value = truckInfoItem.location)
                TextX(label = "Last Updated", value = truckInfoItem.lastUpdateLabel)
            }
        }

    }
}