package com.truck.monitor.app.data.datasource.local

import com.truck.monitor.app.data.database.AppDao
import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.LocalDataSourceImpl
import com.truck.monitor.app.data.datasource.TruckInfoDataEntityMapper
import com.truck.monitor.app.data.model.TruckInfoMapper
import com.truck.monitor.app.data.repository.truckInfoItem1
import com.truck.monitor.app.data.repository.truckInfoItem2
import com.truck.monitor.app.data.repository.truckInfoItem3
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SaveDataLocalDataSourceTests {

    @Mock
    lateinit var appDao: AppDao

    @Mock
    lateinit var mapper: TruckInfoDataEntityMapper

    private lateinit var sut: LocalDataSource

    @Before
    fun setup() {
        sut = LocalDataSourceImpl(
            appDao = appDao,
            truckInfoDataEntityMapper = mapper
        )
    }

    @Test
    fun `saveTruckMonitoringData when list of valid entity is passed then saves in db`() = runTest {
        val localDataEntityList = listOf(
            truckInfoEntity1,
            truckInfoEntity2,
            truckInfoEntity3
        )
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoItemDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3),
        )
        whenever(mapper.toEntitiesList(truckInfoItemDtoList)).thenReturn(localDataEntityList)

        sut.saveTruckMonitoringRemoteData(truckInfoItemDtoList)

        verify(appDao).saveTruckInfoEntitiesList(localDataEntityList)
    }
}