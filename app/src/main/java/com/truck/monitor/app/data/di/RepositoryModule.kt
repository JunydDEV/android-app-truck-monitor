package com.truck.monitor.app.data.di

import com.truck.monitor.app.data.repository.TruckMonitoringRepository
import com.truck.monitor.app.data.repository.TruckMonitoringRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideTruckMonitoringRepository(impl: TruckMonitoringRepositoryImpl): TruckMonitoringRepository
}
