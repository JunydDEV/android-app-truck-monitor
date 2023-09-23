package com.truck.monitor.app.ui.listingscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truck.monitor.app.domain.usecases.FetchTrucksInfoListUseCase
import com.truck.monitor.app.domain.usecases.SearchTrucksInfoUseCase
import com.truck.monitor.app.domain.usecases.SortTrucksInfoListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingScreenModel @Inject constructor(
    private val fetchTrucksInfoListUseCase: FetchTrucksInfoListUseCase,
    private val searchTrucksInfoUseCase: SearchTrucksInfoUseCase,
    private val sortTrucksInfoListingUseCase: SortTrucksInfoListingUseCase
) : ViewModel() {

    val trucksInfoStateFlow = fetchTrucksInfoListUseCase.trucksInfoListStateFlow

    fun fetchTrucksInfoList() {
        viewModelScope.launch {
            fetchTrucksInfoListUseCase.invoke()
        }
    }
}