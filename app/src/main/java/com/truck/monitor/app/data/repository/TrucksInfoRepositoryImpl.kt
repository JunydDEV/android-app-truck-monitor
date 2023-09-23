package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrucksInfoRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDataSource,
    private val localDatasource: LocalDataSource,
) : TrucksInfoRepository {

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