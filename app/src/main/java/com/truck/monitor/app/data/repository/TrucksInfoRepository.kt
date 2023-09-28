package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import kotlinx.coroutines.flow.Flow

interface TrucksInfoRepository {

    /**
     * Fetches truck monitory info list
     *
     * @return list contains info about the truck monitoring i.e. location, driverName etc.
     */
    suspend fun fetchTrucksInfoList(): Flow<DataState>

    /**
     * Search for the truck monitoring info list based on the location.
     *
     * @param query driver/location to fetch the trucks info list.
     * @return list contains the info about the trucks of specified location.
     */
    fun searchTruckInfo(query: String): Flow<DataState>

    /**
     * Sorts the truck monitoring info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     * */
    suspend fun sortListOrdered(order: SortingOrder): Flow<DataState>
}