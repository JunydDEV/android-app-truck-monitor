package com.truck.monitor.app.data.network

import com.truck.monitor.app.data.model.TruckInfoListItem
import retrofit2.http.GET

interface ApiService {

    @GET("/api/candidate")
    suspend fun getTrucksInfoList(): List<TruckInfoListItem>
}