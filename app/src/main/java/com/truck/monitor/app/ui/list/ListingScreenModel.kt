package com.truck.monitor.app.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.model.TruckInfo
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
class ListingScreenModel @Inject constructor(
    private val fetchTrucksInfoListUseCase: FetchTrucksInfoListUseCase,
    private val searchTrucksInfoUseCase: SearchTrucksInfoUseCase,
    private val sortTrucksInfoListingUseCase: SortTrucksInfoListingUseCase
) : ViewModel() {

    private val _trucksInfoStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val trucksInfoStateFlow: StateFlow<UiState> = _trucksInfoStateFlow

    private val _searchTrucksInfoStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val searchTrucksInfoStateFlow: StateFlow<UiState> = _searchTrucksInfoStateFlow

    private val _sortTrucksInfoStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val sortTrucksInfoStateFlow: StateFlow<UiState> = _sortTrucksInfoStateFlow

    fun fetchTrucksInfoList() {
        viewModelScope.launch {
            fetchTrucksInfoListUseCase()
                .onStart {
                    _trucksInfoStateFlow.value = UiState.OnLoading
                }
                .collect {
                    when (it) {
                        is DataState.OnError -> {
                            val errorObject = it.response
                            _trucksInfoStateFlow.value = UiState.OnFailure(
                                error = errorObject.message,
                                errorDescription = errorObject.description
                            )
                        }
                        is DataState.OnSuccess<*> -> {
                            val list = it.response as List<TruckInfo>
                            _trucksInfoStateFlow.value = UiState.OnSuccess(list)
                        }
                    }
                }
        }
    }

    fun searchTrucksInfoList(location: String) {
        viewModelScope.launch {
            searchTrucksInfoUseCase(location)
                .onStart {
                    _searchTrucksInfoStateFlow.value = UiState.OnLoading
                }
                .collect {
                    when (it) {
                        is DataState.OnError -> {
                            val errorObject = it.response
                            _searchTrucksInfoStateFlow.value = UiState.OnFailure(
                                error = errorObject.message,
                                errorDescription = errorObject.description
                            )
                        }
                        is DataState.OnSuccess<*> -> {
                            val list = it.response as List<TruckInfo>
                            _searchTrucksInfoStateFlow.value = UiState.OnSuccess(list)
                        }
                    }
                }
        }
    }

    fun sortTrucksInfoList(trucksInfoList: List<TruckInfo>, sortingOrder: SortingOrder) {
        viewModelScope.launch {
            sortTrucksInfoListingUseCase.invoke(trucksInfoList, sortingOrder)
        }
    }
}