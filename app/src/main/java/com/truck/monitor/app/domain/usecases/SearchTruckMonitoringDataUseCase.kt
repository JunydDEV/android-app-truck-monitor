package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.repository.TruckMonitoringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTruckMonitoringDataUseCase @Inject constructor(
    private val repository: TruckMonitoringRepository
) {
    operator fun invoke(query: String): Flow<DataState> {
        return repository.searchTruckMonitoringData(query)
    }

}