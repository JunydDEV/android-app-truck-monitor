package com.truck.monitor.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.domain.usecases.FetchTruckMonitoringDataUseCase
import com.truck.monitor.app.domain.usecases.SearchTruckMonitoringDataUseCase
import com.truck.monitor.app.domain.usecases.SortTruckMonitoringDataUseCase
import com.truck.monitor.app.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTruckMonitoringDataUseCase: FetchTruckMonitoringDataUseCase,
    private val sortTruckMonitoringDataUseCase: SortTruckMonitoringDataUseCase,
    private val searchTruckMonitoringDataUseCase: SearchTruckMonitoringDataUseCase
) : ViewModel() {

    private val _truckMonitoringStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val truckMonitoringStateFlow: StateFlow<UiState> = _truckMonitoringStateFlow

    fun fetchTruckMonitoringData() {
        viewModelScope.launch {
            fetchTruckMonitoringDataUseCase()
                .onStart { _truckMonitoringStateFlow.value = UiState.OnLoading }
                .collect { parseTruckMonitoringDataState(it) }
        }
    }

    fun sortTruckMonitoringData(sortingOrder: SortingOrder) {
        viewModelScope.launch {
            sortTruckMonitoringDataUseCase(sortingOrder)
                .collect { parseTruckMonitoringDataState(it) }
        }
    }

    fun searchTruckMonitoringData(query: String) {
        if (query.isEmpty()) {
            fetchTruckMonitoringData()
        } else {
            fetchTruckMonitoringDataBy(query)
        }
    }

    private fun fetchTruckMonitoringDataBy(query: String) {
        viewModelScope.launch {
            searchTruckMonitoringDataUseCase(query)
                .collect { parseTruckMonitoringDataState(it) }
        }
    }

    private fun parseTruckMonitoringDataState(it: DataState) {
        when (it) {
            is DataState.OnError -> {
                val errorObject = it.response
                _truckMonitoringStateFlow.value = UiState.OnFailure(
                    error = errorObject.message,
                    errorDescription = errorObject.description
                )
            }

            is DataState.OnSuccess<*> -> {
                val list = it.response.data as List<*>
                _truckMonitoringStateFlow.value = UiState.OnSuccess(list)
            }
        }
    }

}