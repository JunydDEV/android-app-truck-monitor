package com.truck.monitor.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTruckInfoEntitiesList(entities: List<TruckInfoDataEntity>)

    @Query("SELECT * FROM truck_info_data")
    fun getTruckMonitoringLocalData(): List<TruckInfoDataEntity>?

    @Query("SELECT * FROM truck_info_data WHERE driver_name LIKE '%' || :query || '%' OR location LIKE '%' || :query || '%'")
    fun getTruckMonitoringLocalData(query: String): List<TruckInfoDataEntity>?
}