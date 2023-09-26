package com.truck.monitor.app.data.di

import android.content.Context
import androidx.room.Room
import com.truck.monitor.app.data.database.AppDao
import com.truck.monitor.app.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesDispatchers() = Dispatchers.IO

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.appDao()
    }

    companion object {
        private const val DATABASE_NAME = "TruckMonitoringDb"
    }
}