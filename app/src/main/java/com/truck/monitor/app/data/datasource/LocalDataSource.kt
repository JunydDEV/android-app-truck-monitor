package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.TruckInfoListItemDto

interface LocalDataSource {
    /**
     * Fetches truck monitoring info list
     *
     * @return list of truck info data
     */
    suspend fun fetchTruckMonitoringData(): List<TruckInfoListItemDto>

    /**
     * Search for the truck monitoring info list based on the driver name and location.
     *
     * @param query driver name/location to fetch the trucks info list.
     * @return list contains the info about the trucks data against the query.
     */
    suspend fun searchTruckMonitoringData(query: String): List<TruckInfoListItemDto>

    /**
     * Saves remote data of truck monitory into local database
     *
     * @param list contains info about the truck monitoring.
     */
    suspend fun saveTruckMonitoringRemoteData(list: List<TruckInfoListItemDto>)
}