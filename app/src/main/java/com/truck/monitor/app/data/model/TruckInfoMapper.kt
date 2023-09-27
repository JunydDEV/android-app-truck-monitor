package com.truck.monitor.app.data.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.DurationUnit

class TruckInfoMapper(private val dispatcher: CoroutineDispatcher) {

    suspend fun toTruckInfoListItemDto(item: TruckInfoListItem): TruckInfoListItemDto {
        val (lastUpdateInHours, lastUpdateInDays) = convertIntoDays(item.lastUpdated)
        return TruckInfoListItemDto(
            plateNo = item.plateNo,
            image = cleanUpPicSumUrl(item.imageURL),
            driverName = item.driverName,
            location = item.location,
            latitude = item.lat,
            longitude = item.long,
            lastUpdate = lastUpdateInHours,
            lastUpdateLabel = lastUpdateInDays
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

    private suspend fun convertIntoDays(isoString: String): Pair<Int, String> {
        return withContext(dispatcher) {
            try {
                val pastTimeInstant = Instant.parse(isoString)
                val currentTimeInstant = Clock.System.now()
                val duration = currentTimeInstant - pastTimeInstant
                val daysCount = duration.toInt(DurationUnit.DAYS)
                val hoursCount = duration.toInt(DurationUnit.HOURS)
                Pair(hoursCount, prepareDaysCountLabel(daysCount))
            } catch (e: Exception) {
                e.printStackTrace()
                Pair(-1, "NA")
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