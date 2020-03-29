package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import com.rgalka88.notethecode.feature.drawer.mapper.DrawerItemToDrawerRowGroupModelMapper
import com.rgalka88.notethecode.feature.drawer.model.DrawerChildrenModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test

class LoadDrawerItemsTest {

    private val drawerItemRepository: DrawerItemRepository = mockk()
    private val mapper = DrawerItemToDrawerRowGroupModelMapper()

    private val loadDrawerItems = LoadDrawerItems(drawerItemRepository, mapper)


    @Test
    fun `invoke returns expected mapped items from repository`() {
        every { drawerItemRepository.getAll() } returns Observable.just(drawerItems)

        loadDrawerItems(UseCase.None())
            .test()
            .assertValue(drawerRowGroups)
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val throwable = Throwable()
        every { drawerItemRepository.getAll() } returns Observable.error(throwable)

        loadDrawerItems(UseCase.None())
            .test()
            .assertError(throwable)
    }

    private val drawerRowGroups = listOf(
        DrawerRowGroupModel(
            DrawerRowModel(
                drawableRes = 123,
                title = "HashTag1",
                hasChildren = true,
                childrenVisible = true,
                animateArrow = true
            ), DrawerChildrenModel(
                listOf(
                    DrawerRowGroupModel(
                        DrawerRowModel(title = "HashTagA", hasChildren = true),
                        DrawerChildrenModel(
                            listOf(
                                DrawerRowGroupModel(
                                    DrawerRowModel(title = "HashTagA1"),
                                    DrawerChildrenModel(emptyList())
                                )
                            )
                        )
                    ),
                    DrawerRowGroupModel(
                        DrawerRowModel(title = "HashTagB"),
                        DrawerChildrenModel(emptyList())
                    )
                )
            )
        ),
        DrawerRowGroupModel(
            DrawerRowModel(
                drawableRes = 124,
                title = "HashTag2",
                hasChildren = false,
                childrenVisible = false,
                animateArrow = true
            ),
            DrawerChildrenModel(emptyList())
        )
    )

    private val drawerItems = listOf(
        DrawerItemModel(
            title = "HashTag1",
            iconResId = 123,
            expanded = true,
            animateArrow = true
        ),
        DrawerItemModel(
            title = "HashTag2",
            iconResId = 124,
            expanded = false,
            animateArrow = true
        ),

        DrawerItemModel("HashTagA", "HashTag1"),
        DrawerItemModel("HashTagB", "HashTag1"),
        DrawerItemModel("HashTagA1", "HashTagA")
    )

}