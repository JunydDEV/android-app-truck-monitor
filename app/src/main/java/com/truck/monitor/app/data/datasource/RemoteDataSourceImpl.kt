package com.truck.monitor.app.data.datasource

import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.truck.monitor.app.data.InvalidResponseException
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.model.TruckInfoListItem
import com.truck.monitor.app.data.network.ApiService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
) : RemoteDataSource {

    override suspend fun fetchTrucksInfoList(): List<TruckInfoListItem> {
        try {
            return apiService.getTrucksInfoList()
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