package com.truck.monitor.app.data.model

data class TruckInfoListItemDto(
    val plateNo: String,
    val image: String,
    val driverName: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val lastUpdate: Int,
    val lastUpdateLabel: String
)
