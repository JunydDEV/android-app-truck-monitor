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
            val truckInfoDataList = appDao.getTruckInfoEntitiesList()
            truckInfoDataEntityMapper.toDtoList(truckInfoDataList ?: emptyList())
        }
    }

    override suspend fun saveTruckInfoList(list: List<TruckInfoListItemDto>) {
        withContext(dispatcher) {
            appDao.saveTruckInfoEntitiesList(truckInfoDataEntityMapper.toEntitiesList(list))
        }
    }

    override suspend fun searchTruckInfo(query: String): List<TruckInfoListItemDto> {
        return withContext(dispatcher) {
            val truckInfoDataList = appDao.getTruckInfoEntitiesList(query)
            if (truckInfoDataList.isNullOrEmpty()) {
                throw SearchResultNotFoundException("Search results not found")
            } else {
                truckInfoDataEntityMapper.toDtoList(truckInfoDataList)
            }
        }
    }
}

class SearchResultNotFoundException(override val message: String) : Exception(message)