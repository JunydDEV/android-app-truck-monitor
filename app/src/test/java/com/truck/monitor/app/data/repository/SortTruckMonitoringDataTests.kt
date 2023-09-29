package com.truck.monitor.app.data.repository

import app.cash.turbine.test
import com.truck.monitor.app.data.IntegrityCheckFailureException
import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.DataFailureResponse
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoMapper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
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
class SortTruckMonitoringDataTests {
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
    fun `sortTruckMonitoringData when list is unsorted then returns ASC ordered list based on lastUpdate`() =
        runTest {
            val truckInfoDataList = listOf(
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            )
            whenever(localDataSource.fetchTruckMonitoringData()).thenReturn(truckInfoDataList)

            sut.sortTruckMonitoringData(SortingOrder.ASC).test {
                val dataState = awaitItem()
                assertTrue(dataState is DataState.OnSuccess<*>)

                val successState = dataState as DataState.OnSuccess<*>
                assertNotNull(successState.response)
                assertNotNull(successState.response.data)

                val sortedList = successState.response.data as List<*>
                assertNotNull(sortedList)
                assertTrue(sortedList.isNotEmpty())
                assertTrue(sortedList.size == truckInfoDataList.size)
                assertEquals(sortedList[0], truckInfoDataList[2])
                assertEquals(sortedList[1], truckInfoDataList[0])
                assertEquals(sortedList[2], truckInfoDataList[1])

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `sortTruckMonitoringData when sort order is DESC then returns DESC ordered list based on lastUpdate`() =
        runTest {
            val truckInfoDataList = listOf(
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1)
            )
            whenever(localDataSource.fetchTruckMonitoringData()).thenReturn(truckInfoDataList)

            sut.sortTruckMonitoringData(SortingOrder.DESC).test {
                val dataState = awaitItem()
                assertTrue(dataState is DataState.OnSuccess<*>)

                val successState = dataState as DataState.OnSuccess<*>
                assertNotNull(successState.response)
                assertNotNull(successState.response.data)

                val sortedList = successState.response.data as List<*>
                assertNotNull(sortedList)
                assertTrue(sortedList.isNotEmpty())
                assertTrue(sortedList.size == truckInfoDataList.size)
                assertEquals(sortedList[0], truckInfoDataList[1])
                assertEquals(sortedList[1], truckInfoDataList[0])
                assertEquals(sortedList[2], truckInfoDataList[2])

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `sortTruckMonitoringData when fetching data from local database fails with exception then returns`() =
        runTest {
            val integrityCheckFailureTitle = "Local Database Failure"
            val integrityCheckFailureDescription = "Looks like database migration failed due to schema changes, please try later."
            val integrityCheckFailure = IntegrityCheckFailureException(
                title = integrityCheckFailureTitle,
                description = integrityCheckFailureDescription
            )
            val error =
                DataFailureResponse(integrityCheckFailure.title, integrityCheckFailure.description)
            whenever(localDataSource.fetchTruckMonitoringData()).thenAnswer { throw integrityCheckFailure }
            whenever(exceptionHandler.handle(integrityCheckFailure)).thenReturn(error)

            sut.sortTruckMonitoringData(SortingOrder.ASC).test {
                val dataState = awaitItem()
                assertTrue(dataState is DataState.OnError)

                val errorState = dataState as DataState.OnError
                val errorResponse = errorState.response
                assertNotNull(errorResponse)
                assertNotNull(errorResponse.message)
                assertNotNull(errorResponse.description)

                assertEquals(integrityCheckFailureTitle, errorResponse.message)
                assertEquals(integrityCheckFailureDescription, errorResponse.description)

                verify(exceptionHandler).handle(integrityCheckFailure)
                cancelAndIgnoreRemainingEvents()
            }

        }

}