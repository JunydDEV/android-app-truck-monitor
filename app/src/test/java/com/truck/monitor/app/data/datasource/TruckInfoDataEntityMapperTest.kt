package com.truck.monitor.app.data.datasource

import com.truck.monitor.app.data.datasource.local.truckInfoEntity1
import com.truck.monitor.app.data.datasource.local.truckInfoEntity2
import com.truck.monitor.app.data.datasource.local.truckInfoEntity3
import com.truck.monitor.app.data.model.TruckInfoMapper
import com.truck.monitor.app.data.repository.truckInfoItem1
import com.truck.monitor.app.data.repository.truckInfoItem2
import com.truck.monitor.app.data.repository.truckInfoItem3
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TruckInfoDataEntityMapperTest {
    lateinit var sut: TruckInfoDataEntityMapper

    @Before
    fun setup() {
        sut = TruckInfoDataEntityMapper(Dispatchers.IO)
    }

    @Test
    fun `convert DTO list to database entities`() = runTest {
        val truckInfoMapper = TruckInfoMapper()
        val truckInfoDataDtoList = listOf(
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem1),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem2),
            truckInfoMapper.toTruckInfoListItemDto(truckInfoItem3)
        )

        val result = sut.toEntitiesList(truckInfoDataDtoList)

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        assertTrue(truckInfoDataDtoList.size == result.size)
    }

    @Test
    fun `convert entities list to DTO entities`() = runTest {
        val entitiesList = listOf(
            truckInfoEntity1,
            truckInfoEntity2,
            truckInfoEntity3
        )

        val result = sut.toDtoList(entitiesList)

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        assertTrue(entitiesList.size == result.size)
    }
}