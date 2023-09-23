package com.truck.monitor.app.data.network

import com.truck.monitor.app.data.model.TruckInfo
import retrofit2.http.GET

interface ApiService {

    @GET("/api/candidate")
    fun getTrucksInfoList(): List<TruckInfo>
}