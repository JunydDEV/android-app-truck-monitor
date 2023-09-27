package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.database.TruckInfoDataEntity
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TruckInfoDataEntityMapper @Inject constructor(private val dispatcher: CoroutineDispatcher) {
    suspend fun toEntitiesList(list: List<TruckInfoListItemDto>): List<TruckInfoDataEntity> {
        return withContext(dispatcher) {
            list.map {
                TruckInfoDataEntity(
                    plateNo = it.plateNo,
                    driverName = it.driverName,
                    imageUrl = it.image,
                    location = it.location,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    lastUpdated = it.lastUpdate,
                    lastUpdatedLabel = it.lastUpdateLabel
                )
            }
        }
    }

    suspend fun toDtoList(list: List<TruckInfoDataEntity>): List<TruckInfoListItemDto> {
        return withContext(dispatcher) {
            list.map {
                TruckInfoListItemDto(
                    plateNo = it.plateNo,
                    driverName = it.driverName,
                    image = it.imageUrl,
                    location = it.location,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    lastUpdate = it.lastUpdated,
                    lastUpdateLabel = it.lastUpdatedLabel
                )
            }
        }
    }
}