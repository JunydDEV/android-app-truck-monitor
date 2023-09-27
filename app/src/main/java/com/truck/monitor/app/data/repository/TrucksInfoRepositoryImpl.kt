package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.DataSuccessResponse
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import com.truck.monitor.app.data.model.TruckInfoMapper
import kotlinx.coroutines.CoroutineDispatcher
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
    private val dispatcher: CoroutineDispatcher
) : TrucksInfoRepository {

    override suspend fun fetchTrucksInfoList(): Flow<DataState> = flow {
        runCatching {
            remoteDatasource.fetchTrucksInfoList()
        }.onFailure { exception ->
            exception.printStackTrace()
            handleException(exception)
        }.onSuccess { response ->
            val result = response.map { truckInfoMapper.toTruckInfoListItemDto(it) }
            localDatasource.saveTruckInfoList(result)
            val dataSuccessResponse = DataSuccessResponse(result)
            emit(DataState.OnSuccess(dataSuccessResponse))
        }
    }.flowOn(dispatcher)

    private suspend fun FlowCollector<DataState>.handleException(exception: Throwable) {
        val truckInfoListItemDtos = localDatasource.fetchTrucksInfoList()
        if (truckInfoListItemDtos.isEmpty()) {
            val dataFailureResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(dataFailureResponse))
        } else {
            emit(DataState.OnSuccess(DataSuccessResponse(truckInfoListItemDtos)))
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
    }.flowOn(dispatcher)

    override suspend fun sortListOrdered(order: SortingOrder): Flow<DataState> = flow {
        runCatching {
            val truckInfoListItemDtos = localDatasource.fetchTrucksInfoList()
            if (order == SortingOrder.ASC) {
                truckInfoListItemDtos.sortedBy { it.lastUpdate }
            } else {
                truckInfoListItemDtos.sortedByDescending { it.lastUpdate }
            }
        }.onFailure { exception ->
            exception.printStackTrace()
            val dataFailureResponse = exceptionHandler.handle(exception)
            emit(DataState.OnError(dataFailureResponse))
        }.onSuccess { sortedList ->
            emit(DataState.OnSuccess(DataSuccessResponse(sortedList)))
        }
    }.flowOn(dispatcher)
}