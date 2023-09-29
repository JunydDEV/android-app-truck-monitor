package com.truck.monitor.app.data.repository

import app.cash.turbine.test
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataFailureResponse
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.TruckInfoMapper
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FetchTruckMonitoringDataTests {
    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var exceptionHandler: ExceptionHandler

    private lateinit var truckInfoMapper: TruckInfoMapper
    private lateinit var sut: TruckMonitoringRepository

    @Before
    fun setup() {
        truckInfoMapper = TruckInfoMapper(Dispatchers.IO)

        sut = TruckMonitoringRepositoryImpl(
            remoteDatasource = remoteDataSource,
            localDatasource = localDataSource,
            truckInfoMapper = truckInfoMapper,
            exceptionHandler = exceptionHandler,
            dispatcher = Dispatchers.IO
        )
    }

    @Test
    fun `fetchTrucksInfoList when remote data source returns a valid response and stores data locally`() =
        runTest {
            val truckInfoDataList = listOf(
                validTruckInfoItem1,
                validTruckInfoItem2,
                validTruckInfoItem3
            )
            whenever(remoteDataSource.fetchTruckMonitoringData()).thenReturn(truckInfoDataList)

            sut.fetchTruckMonitoringData().test {
                val dataState = awaitItem()
                TestCase.assertTrue(dataState is DataState.OnSuccess<*>)

                val successState = dataState as DataState.OnSuccess<*>
                TestCase.assertNotNull(successState.response)
                TestCase.assertNotNull(successState.response.data)

                val truckInfoListItemDto = successState.response.data as List<*>
                TestCase.assertNotNull(truckInfoListItemDto)
                TestCase.assertTrue(truckInfoListItemDto.isNotEmpty())
                TestCase.assertTrue(truckInfoListItemDto.size == 3)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchTrucksInfoList when network is unavailable and local database is empty then returns DataFailure`() =
        runTest {
            val networkErrorTitle = "Network Error"
            val networkErrorDescription = "No Network Error Description"
            val noNetworkException = NoNetworkException(
                title = networkErrorTitle,
                description = networkErrorDescription
            )
            val error =
                DataFailureResponse(noNetworkException.title, noNetworkException.description)

            whenever(remoteDataSource.fetchTruckMonitoringData()).thenAnswer { throw noNetworkException }
            whenever(localDataSource.fetchTruckMonitoringData()).thenReturn(emptyList())
            whenever(exceptionHandler.handle(noNetworkException)).thenReturn(error)

            sut.fetchTruckMonitoringData().test {
                val dataState = awaitItem()
                TestCase.assertTrue(dataState is DataState.OnError)

                val errorState = dataState as DataState.OnError
                val errorResponse = errorState.response
                TestCase.assertNotNull(errorResponse)
                TestCase.assertNotNull(errorResponse.message)
                TestCase.assertNotNull(errorResponse.description)

                TestCase.assertEquals(networkErrorTitle, errorResponse.message)
                TestCase.assertEquals(networkErrorDescription, errorResponse.description)

                verify(exceptionHandler).handle(noNetworkException)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchTrucksInfoList when network is unavailable and local database has the data then returns DataSuccess`() =
        runTest {
            val networkErrorTitle = "Network Error"
            val networkErrorDescription = "No Network Error Description"
            val noNetworkException = NoNetworkException(
                title = networkErrorTitle,
                description = networkErrorDescription
            )
            val truckInfoDataDtoList = listOf(
                truckInfoMapper.toTruckInfoListItemDto(validTruckInfoItem1),
                truckInfoMapper.toTruckInfoListItemDto(validTruckInfoItem2),
                truckInfoMapper.toTruckInfoListItemDto(validTruckInfoItem3)
            )

            whenever(remoteDataSource.fetchTruckMonitoringData()).thenAnswer { throw noNetworkException }
            whenever(localDataSource.fetchTruckMonitoringData()).thenReturn(truckInfoDataDtoList)

            sut.fetchTruckMonitoringData().test {
                val dataState = awaitItem()
                TestCase.assertTrue(dataState is DataState.OnSuccess<*>)

                val successState = dataState as DataState.OnSuccess<*>
                TestCase.assertNotNull(successState.response)
                TestCase.assertNotNull(successState.response.data)

                val truckInfoListItemDto = successState.response.data as List<*>
                TestCase.assertNotNull(truckInfoListItemDto)
                TestCase.assertTrue(truckInfoListItemDto.isNotEmpty())
                TestCase.assertTrue(truckInfoListItemDto.size == 3)

                cancelAndIgnoreRemainingEvents()
            }
        }
}