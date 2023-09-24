package com.truck.monitor.app.ui

import androidx.lifecycle.ViewModel
import com.truck.monitor.app.domain.usecases.FetchTrucksInfoListUseCase
import com.truck.monitor.app.domain.usecases.SearchTrucksInfoUseCase
import com.truck.monitor.app.domain.usecases.SortTrucksInfoListingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTrucksInfoListUseCase: FetchTrucksInfoListUseCase,
    private val searchTrucksInfoUseCase: SearchTrucksInfoUseCase,
    private val sortTrucksInfoListingUseCase: SortTrucksInfoListingUseCase
): ViewModel() {

}