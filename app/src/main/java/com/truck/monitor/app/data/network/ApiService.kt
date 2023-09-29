package com.truck.monitor.app.data.network

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.model.TruckInfoListItem
import retrofit2.http.GET
import java.io.IOException

interface ApiService {

    @GET("/api/candidate")
    @Throws(IOException::class, JsonParseException::class)
    suspend fun fetchTruckMonitoringData() : List<TruckInfoListItem>
}