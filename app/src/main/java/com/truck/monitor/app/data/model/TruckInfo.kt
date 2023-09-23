package com.truck.monitor.app.data.model

data class TruckInfo(
    val plotNo: Int,
    val driverName: String,
    val profileImage: String,
    val lat: Double,
    val long: Double,
    val location: String,
    val lastUpdate: Int
)