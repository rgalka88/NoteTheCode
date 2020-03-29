package com.rgalka88.notethecode.feature.drawer.mapper

import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerChildrenModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowModel
import org.junit.Assert.assertEquals
import org.junit.Test

class DrawerItemToDrawerRowGroupModelMapperTest {

    private val mapper = DrawerItemToDrawerRowGroupModelMapper()

    @Test
    fun `map transforms drawerItems to expected drawerRowGroups`() {
        val expected = drawerRowGroups
        val actual = mapper.map(drawerItems)

        assertEquals(expected, actual)
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