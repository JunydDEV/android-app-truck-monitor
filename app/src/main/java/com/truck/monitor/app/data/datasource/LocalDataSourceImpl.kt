package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(): LocalDataSource {

    override fun fetchTrucksInfoList(): List<TruckInfoListItem> {
        TODO("Not yet implemented")
    }

    override fun searchTruckInfo(location: String): List<TruckInfoListItem> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): List<TruckInfoListItem> {
        TODO("Not yet implemented")
    }

    override fun saveTruckInfoList(response: List<TruckInfoListItem>) {
    }

}