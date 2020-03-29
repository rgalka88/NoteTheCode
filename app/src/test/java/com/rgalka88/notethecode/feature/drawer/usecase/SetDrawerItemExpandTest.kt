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

class SetDrawerItemExpandTest {

    private val repository: DrawerItemRepository = mockk()
    private val query = DataSource.Query(repository)
    private val setDrawerItemExpand = SetDrawerItemExpand(repository)

    @Before
    fun setup() {
        every { repository.query() } returns query
    }

    @Test
    fun `invoke save item with expected expanded status and animation status when is collapsed`() {
        val model = DrawerItemModel("123", "parent", 123, false, animateArrow = false)
        val expectedModel = model.copy(expanded = true, animateArrow = true)
        every { query.findFirst() } returns Observable.just(model)

        setDrawerItemExpand("123").test()

        verify { repository.save(expectedModel) }
    }

    @Test
    fun `invoke save item with expected expanded status and animation status when is expanded`() {
        val model = DrawerItemModel("123", "parent", 123, true, animateArrow = false)
        val expectedModel = model.copy(expanded = false, animateArrow = true)
        every { query.findFirst() } returns Observable.just(model)

        setDrawerItemExpand("123").test()

        verify { repository.save(expectedModel) }
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val throwable = Throwable()
        every { query.findFirst() } returns Observable.error(throwable)

        setDrawerItemExpand("123")
            .test()
            .assertError(throwable)
    }

    @Test
    fun `invoke returns error when error ocscurs`() {
        val throwable = Throwable()
        every { query.findFirst() } returns Observable.error(throwable)

        setDrawerItemExpand("123")
            .test()
            .assertError(throwable)
    }
}