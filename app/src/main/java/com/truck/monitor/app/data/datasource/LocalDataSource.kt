package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem

interface LocalDataSource {
    /**
     * Fetches truck info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     */
    fun fetchTrucksInfoList(): List<TruckInfoListItem>

    /**
     * Search for the truck info list based on the location.
     *
     * @param location address to fetch the trucks info list.
     * @return list contains the info about the trucks of specified location.
     */
    fun searchTruckInfo(location: String): List<TruckInfoListItem>

    /**
     * Sorts the truck info in ascending or descending order.
     *
     * @param order of listing the truck info in specific order i.e. ASCENDING or DESCENDING
     * @return list in specific order
     */
    fun sortTrucksInfo(order: SortingOrder): List<TruckInfoListItem>

    /**
     * Saves remote data into local database
     */
    fun saveTruckInfoList(response: List<TruckInfoListItem>)
}