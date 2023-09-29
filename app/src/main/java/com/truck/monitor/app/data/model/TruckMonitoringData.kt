package com.truck.monitor.app.data.model

import com.truck.monitor.app.data.model.TruckInfoListItemDto
import javax.annotation.concurrent.Immutable

@Immutable
data class TruckMonitoringData(val list: List<TruckInfoListItemDto>)