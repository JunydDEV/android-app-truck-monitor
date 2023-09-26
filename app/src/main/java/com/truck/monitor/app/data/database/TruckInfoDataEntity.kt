package com.truck.monitor.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "truck_info_data")
data class TruckInfoDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "plate_no") val plateNo: String,
    @ColumnInfo(name = "driver_name") val driverName: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "last_updated") val lastUpdated: String
)