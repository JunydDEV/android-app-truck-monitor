package com.truck.monitor.app.data.datasource

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.InvalidResponseException
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.network.ApiService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : RemoteDataSource {

    @Throws(NoNetworkException::class, JsonParseException::class)
    override suspend fun fetchTruckMonitoringData(): List<TruckInfoListItem> {
        try {
            return apiService.fetchTruckMonitoringData()
        } catch (e: IOException) {
            throw NoNetworkException(
                title = "Network Error",
                description = "Couldn't connect to the internet, please try later"
            )
        } catch (e: JsonParseException) {
            throw InvalidResponseException(
                title = "Response Parsing Error",
                description = "Couldn't parse the server response, please try later"
            )
        }
    }
}