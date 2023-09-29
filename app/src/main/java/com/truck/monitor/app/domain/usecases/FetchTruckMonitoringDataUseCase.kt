package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.datastate.DataState
import com.truck.monitor.app.data.repository.TruckMonitoringRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTruckMonitoringDataUseCase @Inject constructor(
    private val repository: TruckMonitoringRepository
) {

    operator fun invoke(): Flow<DataState> {
        return repository.fetchTruckMonitoringData()
    }
}