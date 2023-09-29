package com.truck.monitor.app.data.datasource.local

import com.truck.monitor.app.data.IntegrityCheckFailureException
import com.truck.monitor.app.data.database.AppDao
import com.truck.monitor.app.data.database.TruckInfoDataEntity
import com.truck.monitor.app.data.datasource.LocalDataSource
import com.truck.monitor.app.data.datasource.LocalDataSourceImpl
import com.truck.monitor.app.data.datasource.TruckInfoDataEntityMapper
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FetchDataLocalDataSourceTests {

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
    fun `fetchTruckMonitoringData when data is available in local db THEN returns non empty list`() = runTest {
        val localDataEntityList = listOf(
            truckInfoEntity1,
            truckInfoEntity2,
            truckInfoEntity3
        )
        val truckInfoDataEntityMapper = TruckInfoDataEntityMapper(Dispatchers.IO)
        val truckInfoItemDtoList = truckInfoDataEntityMapper.toDtoList(localDataEntityList)
        whenever(appDao.getTruckMonitoringLocalData()).thenReturn(localDataEntityList)
        whenever(mapper.toDtoList(localDataEntityList)).thenReturn(truckInfoItemDtoList)

        val result = sut.fetchTruckMonitoringData()

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        assertTrue(localDataEntityList.size == result.size)
    }

    @Test
    fun `fetchTruckMonitoringData when data is unavailable in local db THEN returns empty list`() = runTest {
        val localDataList = emptyList<TruckInfoDataEntity>()
        whenever(appDao.getTruckMonitoringLocalData()).thenReturn(localDataList)
        whenever(mapper.toDtoList(localDataList)).thenReturn(emptyList())

        val result = sut.fetchTruckMonitoringData()

        assertNotNull(result)
        assertTrue(result.isEmpty())
    }

    @Test(expected = IntegrityCheckFailureException::class)
    fun `fetchTruckMonitoringData when database fails with exception THEN throws exception`() = runTest {
        whenever(appDao.getTruckMonitoringLocalData()).thenThrow(IllegalStateException())

        sut.fetchTruckMonitoringData()
    }
}