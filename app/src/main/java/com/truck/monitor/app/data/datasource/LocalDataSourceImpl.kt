package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.database.AppDao
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao,
    private val truckInfoDataEntityMapper: TruckInfoDataEntityMapper,
    private val dispatcher: CoroutineDispatcher
) : LocalDataSource {

    override suspend fun fetchTrucksInfoList(): List<TruckInfoListItemDto> {
        return withContext(dispatcher) {
            truckInfoDataEntityMapper.toDtoList(appDao.getTruckInfoEntitiesList() ?: emptyList())
        }
    }

    override suspend fun saveTruckInfoList(list: List<TruckInfoListItemDto>) {
        withContext(dispatcher) {
            appDao.saveTruckInfoEntitiesList(truckInfoDataEntityMapper.toEntitiesList(list))
        }
    }

    override suspend fun searchTruckInfo(location: String): List<TruckInfoListItemDto> {
        TODO("Not yet implemented")
    }

    override suspend fun sortTrucksInfo(order: SortingOrder): List<TruckInfoListItemDto> {
        TODO("Not yet implemented")
    }
}