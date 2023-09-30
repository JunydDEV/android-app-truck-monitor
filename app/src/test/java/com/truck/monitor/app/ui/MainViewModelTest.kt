package com.truck.monitor.app.ui

import app.cash.turbine.test
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfoListItemDto
import com.truck.monitor.app.data.model.TruckInfoMapper
import com.truck.monitor.app.data.model.datastate.DataFailureResponse
import com.truck.monitor.app.data.model.datastate.DataState
import com.truck.monitor.app.data.model.datastate.DataSuccessResponse
import com.truck.monitor.app.data.repository.truckInfoItem1
import com.truck.monitor.app.data.repository.truckInfoItem2
import com.truck.monitor.app.data.repository.truckInfoItem3
import com.truck.monitor.app.domain.usecases.FetchTruckMonitoringDataUseCase
import com.truck.monitor.app.domain.usecases.SearchTruckMonitoringDataUseCase
import com.truck.monitor.app.domain.usecases.SortTruckMonitoringDataUseCase
import com.truck.monitor.app.ui.state.UiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    lateinit var fetchTruckMonitoringDataUseCase: FetchTruckMonitoringDataUseCase
    @Mock
    lateinit var searchTruckMonitoringDataUseCase: SearchTruckMonitoringDataUseCase
    @Mock
    lateinit var sortTruckMonitoringDataUseCase: SortTruckMonitoringDataUseCase

    private lateinit var sut: MainViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        sut = MainViewModel(
            fetchTruckMonitoringDataUseCase,
            sortTruckMonitoringDataUseCase,
            searchTruckMonitoringDataUseCase
        )
    }

    @Test
    fun `fetchTruckMonitoringData when use case fetches data successfully then produces data to UI with success state`() = runTest {
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoItemDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),

        )
        val flowWithSuccess = flowOf(
            DataState.OnSuccess(DataSuccessResponse(truckInfoItemDtoList))
        )
        whenever(fetchTruckMonitoringDataUseCase.invoke()).thenReturn(flowWithSuccess)

        sut.fetchTruckMonitoringData()
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateLoading = awaitItem()
            assertTrue(uiStateLoading is UiState.OnLoading)
            val uiStateSuccess = awaitItem()
            assertTrue(uiStateSuccess is UiState.OnSuccess<*>)
            val data = (uiStateSuccess as UiState.OnSuccess<*>).data
            assertTrue(data is List<*>)
            val list = data as List<TruckInfoListItemDto>
            assertNotNull(list)
            assertTrue(list.isNotEmpty())
            assertTrue(truckInfoItemDtoList.size == list.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchTruckMonitoringData when use case fails then produces data to UI with failure state`() = runTest {
        val errorTitle = "Network Error"
        val errorDescription = "Network error description"
        val flowWithError = flowOf(
            DataState.OnError(DataFailureResponse(errorTitle, errorDescription))
        )
        whenever(fetchTruckMonitoringDataUseCase.invoke()).thenReturn(flowWithError)

        sut.fetchTruckMonitoringData()
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateLoading = awaitItem()
            assertTrue(uiStateLoading is UiState.OnLoading)
            val uiStateFailure = awaitItem()
            assertTrue(uiStateFailure is UiState.OnFailure)
            val title = (uiStateFailure as UiState.OnFailure).error
            val description = uiStateFailure.errorDescription
            assertEquals(errorTitle, title)
            assertEquals(errorDescription, description)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sortTruckMonitoringData when use case fetch data successfully then produces data for UI with success state`() = runTest {
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoItemDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),

            )
        val flowWithSuccess = flowOf(
            DataState.OnSuccess(DataSuccessResponse(truckInfoItemDtoList))
        )
        whenever(sortTruckMonitoringDataUseCase.invoke(SortingOrder.ASC)).thenReturn(flowWithSuccess)

        sut.sortTruckMonitoringData(SortingOrder.ASC)
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateSuccess = awaitItem()
            assertTrue(uiStateSuccess is UiState.OnSuccess<*>)
            val data = (uiStateSuccess as UiState.OnSuccess<*>).data
            assertTrue(data is List<*>)
            val list = data as List<TruckInfoListItemDto>
            assertNotNull(list)
            assertTrue(list.isNotEmpty())
            assertTrue(truckInfoItemDtoList.size == list.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sortTruckMonitoringData when use case fails then produces data for UI with failure state`() = runTest {
        val errorTitle = "Local Database Failure"
        val errorDescription = "Local database failure description"
        val flowWithError = flowOf(
            DataState.OnError(DataFailureResponse(errorTitle, errorDescription))
        )
        whenever(sortTruckMonitoringDataUseCase.invoke(SortingOrder.ASC)).thenReturn(flowWithError)

        sut.sortTruckMonitoringData(SortingOrder.ASC)
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateFailure = awaitItem()
            assertTrue(uiStateFailure is UiState.OnFailure)
            val title = (uiStateFailure as UiState.OnFailure).error
            val description = uiStateFailure.errorDescription
            assertEquals(errorTitle, title)
            assertEquals(errorDescription, description)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchTruckMonitoringData when query is empty then fetches all data`() = runTest {
        val query = ""
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoItemDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),

            )
        val flowWithSuccess = flowOf(
            DataState.OnSuccess(DataSuccessResponse(truckInfoItemDtoList))
        )
        whenever(fetchTruckMonitoringDataUseCase.invoke()).thenReturn(flowWithSuccess)

        sut.searchTruckMonitoringData(query)
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateLoading = awaitItem()
            assertTrue(uiStateLoading is UiState.OnLoading)
            val uiStateSuccess = awaitItem()
            assertTrue(uiStateSuccess is UiState.OnSuccess<*>)
            val data = (uiStateSuccess as UiState.OnSuccess<*>).data
            assertTrue(data is List<*>)
            val list = data as List<TruckInfoListItemDto>
            assertNotNull(list)
            assertTrue(list.isNotEmpty())
            assertTrue(truckInfoItemDtoList.size == list.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchTruckMonitoringData when query is non empty then fetches records against the query`() = runTest {
        val query = "Ali"
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoItemDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1)

            )
        val flowWithSuccess = flowOf(
            DataState.OnSuccess(DataSuccessResponse(truckInfoItemDtoList))
        )
        whenever(searchTruckMonitoringDataUseCase.invoke(query)).thenReturn(flowWithSuccess)

        sut.searchTruckMonitoringData(query)
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateSuccess = awaitItem()
            assertTrue(uiStateSuccess is UiState.OnSuccess<*>)
            val data = (uiStateSuccess as UiState.OnSuccess<*>).data
            assertTrue(data is List<*>)
            val list = data as List<TruckInfoListItemDto>
            assertNotNull(list)
            assertTrue(list.isNotEmpty())
            assertTrue(truckInfoItemDtoList.size == list.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `searchTruckMonitoringData when query is non empty and doesn't exits in db then fetches search not found error`() = runTest {
        val query = "Lorem Ipsum"
        val errorTitle = "Search Results Not Found"
        val errorDescription = "Search results not found description"
        val flowWithError = flowOf(
            DataState.OnError(DataFailureResponse(errorTitle, errorDescription))
        )
        whenever(searchTruckMonitoringDataUseCase.invoke(query)).thenReturn(flowWithError)

        sut.searchTruckMonitoringData(query)
        sut.truckMonitoringStateFlow.test {
            val uiStateStart = awaitItem()
            assertTrue(uiStateStart is UiState.OnStart)
            val uiStateFailure = awaitItem()
            assertTrue(uiStateFailure is UiState.OnFailure)
            val title = (uiStateFailure as UiState.OnFailure).error
            val description = uiStateFailure.errorDescription
            assertEquals(errorTitle, title)
            assertEquals(errorDescription, description)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}