package com.truck.monitor.app.domain.usecases

import com.truck.monitor.app.data.repository.TrucksInfoRepository
import javax.inject.Inject

class SortTrucksInfoListingUseCase @Inject constructor(
    private val repository: TrucksInfoRepository
) {

}