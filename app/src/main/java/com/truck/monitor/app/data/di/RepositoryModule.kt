package com.truck.monitor.app.data.di

import com.truck.monitor.app.data.repository.TrucksInfoRepository
import com.truck.monitor.app.data.repository.TrucksInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideTrucksInfoRepository(impl: TrucksInfoRepositoryImpl): TrucksInfoRepository
}
