package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.repository.TrucksInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTrucksInfoUseCase @Inject constructor(
    private val repository: TrucksInfoRepository
) {
    operator fun invoke(query: String): Flow<DataState> {
        return repository.searchTruckInfo(query)
    }

}