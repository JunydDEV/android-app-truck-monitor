package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.IntegrityCheckFailureException
import com.truck.monitor.app.data.SearchResultNotFoundException
import com.truck.monitor.app.data.database.AppDao
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    private val appDao: AppDao,
    private val truckInfoDataEntityMapper: TruckInfoDataEntityMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {

    override suspend fun fetchTrucksInfoList(): List<TruckInfoListItemDto> {
        return withContext(dispatcher) {
            try {
                val truckInfoDataList = appDao.getTruckInfoEntitiesList()
                truckInfoDataEntityMapper.toDtoList(truckInfoDataList ?: emptyList())
            } catch (e: IllegalStateException) {
                throw IntegrityCheckFailureException(
                    title = "Local Database Failure",
                    description = "Looks like database migration failed due to schema changes, please try later."
                )
            }
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
                throw SearchResultNotFoundException(
                    title = "Search Result Not Found",
                    description = "Couldn't found any results against your query, try different query"
                )
            } else {
                truckInfoDataEntityMapper.toDtoList(truckInfoDataList)
            }
        }
    }
}