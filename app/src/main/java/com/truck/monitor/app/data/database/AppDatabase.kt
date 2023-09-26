package com.truck.monitor.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TruckInfoDataEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}