package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.repository.TrucksInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortTrucksInfoListingUseCase @Inject constructor(private val repository: TrucksInfoRepository) {

    suspend fun sortListOrdered(sortingOrder: SortingOrder): Flow<DataState> {
        return repository.sortListOrdered(sortingOrder)
    }
}