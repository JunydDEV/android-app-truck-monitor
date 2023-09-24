package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.model.DataState
import kotlinx.coroutines.flow.Flow

interface TrucksInfoRepository {

    /**
     * Fetches truck info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     */
    suspend fun fetchTrucksInfoList(): Flow<DataState>

    /**
     * Search for the truck info list based on the location.
     *
     * @param location address to fetch the trucks info list.
     * @return list contains the info about the trucks of specified location.
     */
    fun searchTruckInfo(location: String): Flow<DataState>
}