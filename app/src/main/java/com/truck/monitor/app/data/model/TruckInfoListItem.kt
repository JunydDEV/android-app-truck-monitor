package com.truck.monitor.app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TruckInfoListItem(
    @Expose @SerializedName("plateNo") val plateNo: String,
    @Expose @SerializedName("driverName") val driverName: String,
    @Expose @SerializedName("imageURL") val imageURL: String,
    @Expose @SerializedName("lat") val lat: Double,
    @Expose @SerializedName("lng") val long: Double,
    @Expose @SerializedName("location") val location: String,
    @Expose @SerializedName("lastUpdated") val lastUpdated: String
)