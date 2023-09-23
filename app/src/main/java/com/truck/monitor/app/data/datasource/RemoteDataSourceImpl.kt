package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo
import com.truck.monitor.app.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override fun fetchTrucksInfoList(): List<TruckInfo> {
        return apiService.getTrucksInfoList()
    }

    override fun searchTruckInfo(location: String): List<TruckInfo> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): List<TruckInfo> {
        TODO("Not yet implemented")
    }
}