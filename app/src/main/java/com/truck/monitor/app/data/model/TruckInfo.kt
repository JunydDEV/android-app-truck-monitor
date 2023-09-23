package com.truck.monitor.app.data.model

data class TruckInfo(
    val plateNo: Int,
    val driverName: String,
    val imageURL: String,
    val lat: Double,
    val long: Double,
    val location: String,
    val lastUpdated: String
)