package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.model.DataState
import com.truck.monitor.app.data.repository.TrucksInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTrucksInfoListUseCase @Inject constructor(private val repository: TrucksInfoRepository) {

    suspend operator fun invoke(): Flow<DataState> {
        return repository.fetchTrucksInfoList()
    }
}