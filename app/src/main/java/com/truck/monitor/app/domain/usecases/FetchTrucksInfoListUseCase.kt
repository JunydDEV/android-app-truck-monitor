package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.TruckInfo
import com.truck.monitor.app.data.repository.TrucksInfoRepository
import com.truck.monitor.app.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchTrucksInfoListUseCase @Inject constructor(
    private val repository: TrucksInfoRepository
) {
    private val _trucksInfoListStateFlow = MutableStateFlow<UiState>(UiState.OnStart)
    val trucksInfoListStateFlow: StateFlow<UiState> = _trucksInfoListStateFlow

    suspend operator fun invoke() {
        repository.fetchTrucksInfoList()
            .onStart {
                _trucksInfoListStateFlow.value = UiState.OnLoading
            }
            .collect {
                when (it) {
                    is DataState.OnError -> {
                        val errorObject = it.response
                        _trucksInfoListStateFlow.value = UiState.OnFailure(
                            error = errorObject.message,
                            errorDescription = errorObject.description
                        )
                    }

                    is DataState.OnSuccess<*> -> {
                        val list = it.response as List<TruckInfo>
                        _trucksInfoListStateFlow.value = UiState.OnSuccess(list)
                    }
                }
            }
    }
}