package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun fetchTrucksInfoList(): List<TruckInfoListItem> {
        return apiService.getTrucksInfoList()
    }

    override fun searchTruckInfo(location: String): List<TruckInfoListItem> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): List<TruckInfoListItem> {
        TODO("Not yet implemented")
    }
}