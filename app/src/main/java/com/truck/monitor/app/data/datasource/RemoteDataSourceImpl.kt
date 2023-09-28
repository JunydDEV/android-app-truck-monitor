package com.truck.monitor.app.data.datasource

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
}