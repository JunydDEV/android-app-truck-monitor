package com.truck.monitor.app.data.datasource

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.model.TruckInfoListItem
import java.io.IOException


interface RemoteDataSource {

    /**
     * Fetches truck info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     */
    suspend fun fetchTruckMonitoringData(): List<TruckInfoListItem>
}