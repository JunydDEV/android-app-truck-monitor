package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.model.TruckInfoListItem

val validTruckInfoItem1 = TruckInfoListItem(
    plateNo = "DXB123",
    driverName = "Ahmad Ali",
    imageURL = "https://imageUrl.com",
    lat = 1.0,
    long = 2.0,
    location = "Jumaira Dubai",
    lastUpdated = "2023-09-28T19:00:33+00:00"
)

val validTruckInfoItem2 = TruckInfoListItem(
    plateNo = "DXB124",
    driverName = "Waheed Ahmad",
    imageURL = "https://imageUrl.com",
    lat = 10.0,
    long = 20.0,
    location = "Reem Abu Dhabi",
    lastUpdated = "2023-09-28T20:00:33+00:00"
)

val validTruckInfoItem3 = TruckInfoListItem(
    plateNo = "DXB125",
    driverName = "Wajid Khan",
    imageURL = "https://imageUrl.com",
    lat = 10.0,
    long = 20.0,
    location = "Yas Abu Dhabi",
    lastUpdated = "2023-09-28T21:00:33+00:00"
)