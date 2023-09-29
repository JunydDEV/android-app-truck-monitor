package com.truck.monitor.app.data.datasource.local

import com.truck.monitor.app.data.SearchResultNotFoundException
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
class SearchDataLocalDataSourceTests {
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
    fun `searchTruckMonitoringData when database finds records against query then returns the list`() =
        runTest {
            val query = "Ali"
            val localDataEntityList = listOf(truckInfoEntity1)
            val truckInfoDataEntityMapper = TruckInfoDataEntityMapper(Dispatchers.IO)
            val resultsList = truckInfoDataEntityMapper.toDtoList(localDataEntityList)
            whenever(appDao.getTruckMonitoringLocalData(query)).thenReturn(localDataEntityList)
            whenever(mapper.toDtoList(localDataEntityList)).thenReturn(resultsList)

            val result = sut.searchTruckMonitoringData(query)

            assertNotNull(result)
            assertTrue(result.isNotEmpty())
            assertTrue(localDataEntityList.size == result.size)
        }

    @Test(expected = SearchResultNotFoundException::class)
    fun `searchTruckMonitoringData when database doesn't find records against query then returns search not found exception`() =
        runTest {
            val query = "Lorem Ipsum"
            val localDataEntityList = emptyList<TruckInfoDataEntity>()
            whenever(appDao.getTruckMonitoringLocalData(query)).thenReturn(localDataEntityList)

            sut.searchTruckMonitoringData(query)
        }
}