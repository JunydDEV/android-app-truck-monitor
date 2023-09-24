package com.truck.monitor.app.ui

import com.truck.monitor.app.data.model.TruckInfoListItem
import javax.annotation.concurrent.Immutable

@Immutable
data class TruckInfoData(val list: List<TruckInfoListItem>)