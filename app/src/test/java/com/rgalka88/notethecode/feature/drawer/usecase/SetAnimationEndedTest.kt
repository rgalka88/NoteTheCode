package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.data.DataSource
import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class SetAnimationEndedTest {

    private val repository: DrawerItemRepository = mockk()
    private val query = DataSource.Query(repository)
    private val setAnimationEnded = SetAnimationEnded(repository)

    @Before
    fun setup() {
        every { repository.query() } returns query
    }

    @Test
    fun `invoke save animation end status`() {
        val model = DrawerItemModel("123", "parent", 123, false, animateArrow = true)
        val expectedModel = model.copy(animateArrow = false)
        every { query.findFirst() } returns Observable.just(model)

        setAnimationEnded("123").test()

        verify { repository.save(expectedModel) }
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val throwable = Throwable()
        every { query.findFirst() } returns Observable.error(throwable)

        setAnimationEnded("123")
            .test()
            .assertError(throwable)
    }
}