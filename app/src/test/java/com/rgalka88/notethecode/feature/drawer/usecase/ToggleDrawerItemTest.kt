package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.BaseUnitTest
import com.rgalka88.notethecode.UnitTestTransformer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

class ToggleDrawerItemTest : BaseUnitTest() {
    private val setDrawerItemExpand: SetDrawerItemExpand = mockk()
    private val setAnimationEnded: SetAnimationEnded = mockk()
    private val toggleDrawerItem = ToggleDrawerItem(setDrawerItemExpand, setAnimationEnded)
    private val computationScheduler = TestScheduler()

    @Test
    fun `invoke calls setDrawerItemExpand`() {
        val params = " title"

        every { setDrawerItemExpand(params) } returns Completable.complete()
        every { setAnimationEnded(params) } returns Completable.complete()

        toggleDrawerItem(params)
            .test()
            .assertComplete()

        verify { setDrawerItemExpand(params) }
    }

    @Test
    fun `invoke calls setAnimationEnded after 200 ms`() {
        val params = " title"
        every { setDrawerItemExpand(params) } returns Completable.complete()
        every { setAnimationEnded(params) } returns Completable.complete()
        overrideSchedulers(UnitTestTransformer(computation = computationScheduler))

        with(toggleDrawerItem(params).test()) {
            assertNotComplete()
            computationScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
            verify(inverse = true) { setAnimationEnded(params) }
            computationScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
            verify { setAnimationEnded(params) }
            assertComplete()
        }
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val params = " title"
        val throwable = Throwable()
        every { setDrawerItemExpand(params) } returns Completable.error(throwable)

        toggleDrawerItem(params)
            .test()
            .assertError(throwable)
    }
}