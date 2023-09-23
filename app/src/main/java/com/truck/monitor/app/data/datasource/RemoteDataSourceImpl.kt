package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo

class RemoteDataSourceImpl: RemoteDataSource {

    override fun fetchTrucksInfoList(): List<TruckInfo> {
        TODO("Not yet implemented")
    }

    override fun searchTruckInfo(location: String): List<TruckInfo> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): List<TruckInfo> {
        TODO("Not yet implemented")
    }
}