package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo
import kotlinx.coroutines.flow.Flow

class TrucksInfoRepositoryImpl: TrucksInfoRepository {

    override fun fetchTrucksInfoList(): Flow<List<TruckInfo>> {
        TODO("Not yet implemented")
    }

    override fun searchTruckInfo(location: String): Flow<List<TruckInfo>> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): Flow<List<TruckInfo>> {
        TODO("Not yet implemented")
    }
}