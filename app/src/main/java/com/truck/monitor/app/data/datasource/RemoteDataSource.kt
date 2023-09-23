package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo

interface RemoteDataSource {

    /**
     * Fetches truck info list
     *
     * @return list contains info about the trucks i.e. location, driverName etc.
     */
    fun fetchTrucksInfoList(): List<TruckInfo>

    /**
     * Search for the truck info list based on the location.
     *
     * @param location address to fetch the trucks info list.
     * @return list contains the info about the trucks of specified location.
     */
    fun searchTruckInfo(location: String): List<TruckInfo>

    /**
     * Sorts the truck info in ascending or descending order.
     *
     * @param order of listing the truck info in specific order i.e. ASCENDING or DESCENDING
     * @return list in specific order
     */
    fun sortTrucksInfo(order: SortingOrder): List<TruckInfo>
}