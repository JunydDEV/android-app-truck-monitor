package com.truck.monitor.app.data.datasource.local

import com.truck.monitor.app.data.database.TruckInfoDataEntity

val truckInfoEntity1 = TruckInfoDataEntity(
    plateNo = "DXB123",
    driverName = "Ahmad Ali",
    imageUrl = "https://imageUrl.com",
    latitude = 1.0,
    longitude = 2.0,
    location = "Jumaira Dubai",
    lastUpdated = 22,
    lastUpdatedLabel = "Today"
)

val truckInfoEntity2 = TruckInfoDataEntity(
    plateNo = "DXB124",
    driverName = "Ahmad Ali",
    imageUrl = "https://imageUrl.com",
    latitude = 1.0,
    longitude = 2.0,
    location = "Jumaira Dubai",
    lastUpdated = 25,
    lastUpdatedLabel = "Yesterday"
)

val truckInfoEntity3 = TruckInfoDataEntity(
    plateNo = "DXB124",
    driverName = "Ahmad Ali",
    imageUrl = "https://imageUrl.com",
    latitude = 1.0,
    longitude = 2.0,
    location = "Jumaira Dubai",
    lastUpdated = 48,
    lastUpdatedLabel = "2 days ago"
)