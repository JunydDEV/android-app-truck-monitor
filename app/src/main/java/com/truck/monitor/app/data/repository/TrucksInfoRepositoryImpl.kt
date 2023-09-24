package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.DataSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrucksInfoRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDataSource,
    private val localDatasource: LocalDataSource,
    private val exceptionHandler: ExceptionHandler
) : TrucksInfoRepository {

    override suspend fun fetchTrucksInfoList(): Flow<DataState> = flow {
        runCatching {
            remoteDatasource.fetchTrucksInfoList()
        }.onFailure { exception ->
            exception.printStackTrace()
            val dataFailureResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(dataFailureResponse))
        }.onSuccess { response ->
            val dataSuccessResponse = DataSuccessResponse(response)
            localDatasource.saveTruckInfoList(response)
            emit(DataState.OnSuccess(dataSuccessResponse))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchTruckInfo(location: String): Flow<DataState> = flow {
        runCatching {
            localDatasource.searchTruckInfo(location)
        }.onFailure { exception ->
            exception.printStackTrace()
            val dataFailureResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(dataFailureResponse))
        }.onSuccess { response ->
            val dataSuccessResponse = DataSuccessResponse(response)
            emit(DataState.OnSuccess(dataSuccessResponse))
        }
    }.flowOn(Dispatchers.IO)
}