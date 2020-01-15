package org.example.moex.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.joda.time.DateTimeUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.only
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.quality.Strictness

/**
 * Created by 5turman on 21.04.2017.
 */
@ExperimentalCoroutinesApi
class SharesRepositoryTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @Mock
    lateinit var localDataSource: SharesDataSource

    @Mock
    lateinit var remoteDataSource: SharesDataSource

    lateinit var repository: SharesRepository

    @Before
    fun setUp() {
        repository = SharesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun getAllWithoutForceUpdate_localDataSourceIsEmpty_returnsRemoteShares() = runBlockingTest {
        val remoteShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(emptyList())
        whenever(remoteDataSource.getAll()).thenReturn(remoteShares)

        assertThat(repository.getAll(false)).isEqualTo(remoteShares)
        verify(localDataSource, atLeastOnce()).put(remoteShares)
    }

    @Test
    fun getAllWithoutForceUpdate_localDataSourceIsNotEmpty_returnsLocalShares() = runBlockingTest {
        val localShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(localShares)

        assertThat(repository.getAll(false)).isEqualTo(localShares)
        verifyZeroInteractions(remoteDataSource)
    }

    @Test
    fun getAllWithForceUpdate_returnsRemoteShares() = runBlockingTest {
        val remoteShares = sampleData()

        whenever(remoteDataSource.getAll()).thenReturn(remoteShares)

        assertThat(repository.getAll(true)).isEqualTo(remoteShares)
        verify(localDataSource, only()).put(remoteShares)
    }

    private fun sampleData(): List<Share> {
        val now = DateTimeUtils.currentTimeMillis()

        return listOf(
            Share("MSNG", "МосЭнерго акции обыкн.", "+МосЭнерго", now, 2.199, 3.43),
            Share("YNDX", "PLLC Yandex N.V. class A shs", "Yandex clA", now, 1266.0, -1.78),
            Share("SBER", "Сбербанк России ПАО ао", "Сбербанк", now, 149.23, -1.82)
        )
    }

}
