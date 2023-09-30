package com.truck.monitor.app.data.repository

import app.cash.turbine.test
import com.truck.monitor.app.data.SearchResultNotFoundException
import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.RemoteDataSource
import com.truck.monitor.app.data.model.datastate.DataFailureResponse
import com.truck.monitor.app.data.model.datastate.DataState
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
class SearchTruckMonitoringDataTests {
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
    fun `searchTruckInfo when local datasource finds the record then returns non-empty results`() =
        runTest {
            val query = "Ali"
            val truckInfoDataDtoList = listOf(
                truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            )
            whenever(localDataSource.searchTruckMonitoringData(query)).thenReturn(truckInfoDataDtoList)

            sut.searchTruckMonitoringData(query).test {
                val dataState = awaitItem()
                assertTrue(dataState is DataState.OnSuccess<*>)

                val successState = dataState as DataState.OnSuccess<*>
                assertNotNull(successState.response)
                assertNotNull(successState.response.data)

                val resultList = successState.response.data as List<*>
                assertNotNull(resultList)
                assertTrue(resultList.isNotEmpty())
                assertTrue(resultList.size == truckInfoDataDtoList.size)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `searchTruckInfo when local datasource doesn't find the record returns failure error object`() =
        runTest {
            val query = "Lorem Ipsum"
            val searchNotFoundTitle = "Search Result Not Found"
            val searchNotFoundDescription =
                "Couldn't found any results against your query, try different query"
            val searchNotFoundException = SearchResultNotFoundException(
                title = searchNotFoundTitle,
                description = searchNotFoundDescription
            )
            val error = DataFailureResponse(
                searchNotFoundException.title,
                searchNotFoundException.description
            )
            whenever(localDataSource.searchTruckMonitoringData(query)).thenAnswer { throw searchNotFoundException }
            whenever(exceptionHandler.handle(searchNotFoundException)).thenReturn(error)

            sut.searchTruckMonitoringData(query).test {
                val dataState = awaitItem()
                assertTrue(dataState is DataState.OnError)

                val errorState = dataState as DataState.OnError
                val errorResponse = errorState.response
                assertNotNull(errorResponse)
                assertNotNull(errorResponse.message)
                assertNotNull(errorResponse.description)

                assertEquals(searchNotFoundTitle, errorResponse.message)
                assertEquals(searchNotFoundDescription, errorResponse.description)

                verify(exceptionHandler).handle(searchNotFoundException)
                cancelAndIgnoreRemainingEvents()
            }
        }
}