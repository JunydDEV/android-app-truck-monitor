package com.truck.monitor.app.data.datasource.remote

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.InvalidResponseException
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSourceImpl
import com.truck.monitor.app.data.network.ApiService
import com.truck.monitor.app.data.repository.truckInfoItem1
import com.truck.monitor.app.data.repository.truckInfoItem2
import com.truck.monitor.app.data.repository.truckInfoItem3
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceImplTest {

    @Mock
    lateinit var apiService: ApiService

    private lateinit var sut: RemoteDataSource

    @Before
    fun setup() {
        sut = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `fetchTruckMonitoringData returns a non empty list`() = runTest {
        val list = listOf(
            truckInfoItem1,
            truckInfoItem2,
            truckInfoItem3
        )
        whenever(apiService.fetchTruckMonitoringData()).thenReturn(list)

        val result = sut.fetchTruckMonitoringData()

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        assertTrue(list.size == result.size)
    }

    @Test(expected = NoNetworkException::class)
    fun `fetchTruckMonitoringData when network is unavailable then throws NoNetworkException`() = runTest {
        whenever(apiService.fetchTruckMonitoringData()).thenThrow(IOException())

        sut.fetchTruckMonitoringData()
    }

    @Test(expected = InvalidResponseException::class)
    fun `fetchTruckMonitoringData1 when response is invalid JSON then throws InvalidResponseException`() = runTest {
        whenever(apiService.fetchTruckMonitoringData()).thenThrow(JsonParseException(""))

        sut.fetchTruckMonitoringData()
    }
}