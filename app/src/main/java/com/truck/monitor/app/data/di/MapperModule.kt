package com.truck.monitor.app.data.di

import com.truck.monitor.app.data.model.TruckInfoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun provideMapperInstance() = TruckInfoMapper(Dispatchers.IO)
}