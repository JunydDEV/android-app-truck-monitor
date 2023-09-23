package com.truck.monitor.app.data.di

import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.LocalDataSourceImpl
import com.truck.monitor.app.data.datasource.RemoteDataSourceImpl
import com.truck.monitor.app.data.datasource.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun provideLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}