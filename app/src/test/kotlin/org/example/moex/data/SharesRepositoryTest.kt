package org.example.moex.data

import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.example.moex.TrampolineSchedulersRule
import org.example.moex.data.model.Share
import org.example.moex.data.source.SharesDataSource
import org.joda.time.DateTimeUtils
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness


/**
 * Created by 5turman on 21.04.2017.
 */
class SharesRepositoryTest {

    @get:Rule val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule val schedulersRule = TrampolineSchedulersRule()

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
    fun getAllWithoutForceUpdate_localDataSourceIsEmpty_returnsRemoteShares() {
        val remoteShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(Single.just(emptyList()))
        whenever(remoteDataSource.getAll()).thenReturn(Single.just(remoteShares))

        repository.getAll(false)
                .test()
                .assertNoErrors()
                .assertValue { it == remoteShares }
                .assertComplete()
    }

    @Test
    fun getAllWithoutForceUpdate_localDataSourceIsNotEmpty_returnsLocalShares() {
        val localShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(Single.just(localShares))

        repository.getAll(false)
                .test()
                .assertNoErrors()
                .assertValue { it == localShares }
                .assertComplete()

        verify(remoteDataSource, never()).getAll()
    }

    @Test
    fun getAllWithoutForceUpdate_caching() {
        val localShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(Single.just(localShares))

        repeat(times = 2) {
            repository.getAll(false)
                    .test()
                    .assertNoErrors()
                    .assertValue { it == localShares }
                    .assertComplete()
        }

        verify(localDataSource, times(1)).getAll()
        verify(remoteDataSource, never()).getAll()
    }

    @Test
    fun getAllWithForceUpdate_localDataSourceIsEmpty_returnsRemoteShares() {
        val remoteShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(Single.just(emptyList()))
        whenever(remoteDataSource.getAll()).thenReturn(Single.just(remoteShares))

        repository.getAll(true)
                .test()
                .assertNoErrors()
                .assertValue { it == remoteShares }
                .assertComplete()
    }

    @Test
    fun getAllWithForceUpdate_localDataSourceIsNotEmpty_returnsLocalAndRemoteShares() {
        val localShares = sampleData()
        val remoteShares = sampleData()

        whenever(localDataSource.getAll()).thenReturn(Single.just(localShares))
        whenever(remoteDataSource.getAll()).thenReturn(Single.just(remoteShares))

        repository.getAll(true)
                .test()
                .assertNoErrors()
                .assertValueAt(0) { it == localShares }
                .assertValueAt(1) { it == remoteShares }
                .assertComplete()
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
