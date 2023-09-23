package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.NetworkResponse
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrucksInfoRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDataSource,
    private val localDatasource: LocalDataSource,
    private val exceptionHandler: ExceptionHandler
) : TrucksInfoRepository {

    override fun fetchTrucksInfoList(): Flow<DataState> = flow {
        runCatching {
            remoteDatasource.fetchTrucksInfoList()
        }.onFailure { exception ->
            exception.printStackTrace()
            val errorResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(errorResponse))
        }.onSuccess { response ->
            val successResponse = NetworkResponse(response)
            emit(DataState.OnSuccess(successResponse))
        }
    }

    override fun searchTruckInfo(location: String): Flow<List<TruckInfo>> {
        TODO("Not yet implemented")
    }

    override fun sortTrucksInfo(order: SortingOrder): Flow<List<TruckInfo>> {
        TODO("Not yet implemented")
    }
}