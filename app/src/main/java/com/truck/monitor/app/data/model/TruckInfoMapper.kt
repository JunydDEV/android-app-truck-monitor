package com.truck.monitor.app.data.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.DurationUnit

class TruckInfoMapper(private val dispatcher: CoroutineDispatcher = IO) {

    suspend fun mapToDto(item: TruckInfoListItem): TruckInfoListItemDto {
        return TruckInfoListItemDto(
            plateNo = item.plateNo,
            image = cleanUpPicSumUrl(item.imageURL),
            driverName = item.driverName,
            address = item.location,
            latitude = item.lat,
            longitude = item.long,
            lastUpdate = convertIntoDays(item.lastUpdated)
        )
    }

    private suspend fun cleanUpPicSumUrl(imageURL: String): String {
        return withContext(dispatcher) {
            try {
                imageURL.replace("https://i.", "https://")
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }

    private suspend fun convertIntoDays(isoString: String): String {
        return withContext(dispatcher) {
            try {
                val pastTimeInstant = Instant.parse(isoString)
                val currentTimeInstant = Clock.System.now()
                val duration = currentTimeInstant - pastTimeInstant
                val daysCount = duration.toInt(DurationUnit.DAYS)
                prepareDaysCountLabel(daysCount)
            } catch (e: Exception) {
                e.printStackTrace()
                "NA"
            }
        }
    }

    private fun prepareDaysCountLabel(daysCount: Int): String {
        return when (daysCount) {
            0 -> "Today"
            1 -> "Yesterday"
            else -> "$daysCount days ago"
        }
    }

}