package com.truck.monitor.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.domain.usecases.FetchTrucksInfoListUseCase
import com.truck.monitor.app.domain.usecases.SearchTrucksInfoUseCase
import com.truck.monitor.app.domain.usecases.SortTrucksInfoListingUseCase
import com.truck.monitor.app.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTrucksInfoListUseCase: FetchTrucksInfoListUseCase,
    private val sortTrucksInfoListingUseCase: SortTrucksInfoListingUseCase,
    private val searchTrucksInfoUseCase: SearchTrucksInfoUseCase
) : ViewModel() {

    private val _trucksInfoStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val trucksInfoStateFlow: StateFlow<UiState> = _trucksInfoStateFlow

    fun fetchTrucksInfoList() {
        viewModelScope.launch {
            fetchTrucksInfoListUseCase()
                .onStart { _trucksInfoStateFlow.value = UiState.OnLoading }
                .collect { parseDataState(it) }
        }
    }

    fun sortListOrdered(sortingOrder: SortingOrder) {
        viewModelScope.launch {
            sortTrucksInfoListingUseCase(sortingOrder)
                .collect { parseDataState(it) }
        }
    }

    fun searchLocation(query: String) {
        if (query.isEmpty()) {
            fetchTrucksInfoList()
        } else {
            fetchTrucksInfoBy(query)
        }
    }

    private fun fetchTrucksInfoBy(query: String) {
        viewModelScope.launch {
            searchTrucksInfoUseCase(query)
                .collect { parseDataState(it) }
        }
    }

    private fun parseDataState(it: DataState) {
        when (it) {
            is DataState.OnError -> {
                val errorObject = it.response
                _trucksInfoStateFlow.value = UiState.OnFailure(
                    error = errorObject.message,
                    errorDescription = errorObject.description
                )
            }

            is DataState.OnSuccess<*> -> {
                val list = it.response.data as List<*>
                _trucksInfoStateFlow.value = UiState.OnSuccess(list)
            }
        }
    }

}