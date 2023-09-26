package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.DataSuccessResponse
import com.truck.monitor.app.data.model.TruckInfoMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrucksInfoRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDataSource,
    private val localDatasource: LocalDataSource,
    private val exceptionHandler: ExceptionHandler,
    private val truckInfoMapper: TruckInfoMapper,
) : TrucksInfoRepository {

    override suspend fun fetchTrucksInfoList(): Flow<DataState> = flow {
        runCatching {
            remoteDatasource.fetchTrucksInfoList()
        }.onFailure { exception ->
            exception.printStackTrace()
            handleException(exception)
        }.onSuccess { response ->
            val result = response.map { truckInfoMapper.toDto(it) }
            val dataSuccessResponse = DataSuccessResponse(result)
            localDatasource.saveTruckInfoList(result)
            emit(DataState.OnSuccess(dataSuccessResponse))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun FlowCollector<DataState>.handleException(exception: Throwable) {
        val localData = localDatasource.fetchTrucksInfoList()
        if (localData.isEmpty()) {
            val dataFailureResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(dataFailureResponse))
        } else {
            emit(DataState.OnSuccess(DataSuccessResponse(localData)))
        }
    }

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