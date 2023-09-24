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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.truck.monitor.app.ui.TruckInfoData

@Composable
fun TruckInfoListingScreen(data: TruckInfoData) {
    val list = data.list
    val size = list.size
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(size) {
            Card {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val truckInfoItem = list[it]
                    AsyncImage(
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(10.dp)),
                        model = truckInfoItem.imageURL,
                        contentDescription = "driver image",
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Plate No: ${truckInfoItem.plateNo}")
                        Text(text = "Driver Name: ${truckInfoItem.driverName}")
                        Text(text = "Location: ${truckInfoItem.location}", maxLines = 1)
                        Text(text = "Last Updated: ${truckInfoItem.lastUpdated}", maxLines = 1)
                    }
                }

            }
        }
    }
}