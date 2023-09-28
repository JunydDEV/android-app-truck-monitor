package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.model.TruckInfoListItemDto

interface LocalDataSource {
    /**
     * Fetches truck info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     */
    suspend fun fetchTrucksInfoList(): List<TruckInfoListItemDto>

    /**
     * Search for the truck info list based on the location.
     *
     * @param query name/location to fetch the trucks info list.
     * @return list contains the info about the trucks of specified location.
     */
    suspend fun searchTruckInfo(query: String): List<TruckInfoListItemDto>

    /**
     * Saves remote data into local database
     *
     * @param list contains info about the trucks i.e. location, driverName etc.
     */
    suspend fun saveTruckInfoList(list: List<TruckInfoListItemDto>)
}