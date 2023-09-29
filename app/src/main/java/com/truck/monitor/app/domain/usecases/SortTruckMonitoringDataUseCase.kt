package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.model.SortingOrder
import com.truck.monitor.app.data.repository.TruckMonitoringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SortTruckMonitoringDataUseCase @Inject constructor(private val repository: TruckMonitoringRepository) {

    operator fun invoke(sortingOrder: SortingOrder): Flow<DataState> {
        return repository.sortTruckMonitoringData(sortingOrder)
    }
}