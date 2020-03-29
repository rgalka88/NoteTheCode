package com.rgalka88.notethecode.feature.drawer.mapper

import com.rgalka88.notethecode.core.platform.Mapper
import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerChildrenModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowModel
import javax.inject.Inject

class DrawerItemToDrawerRowGroupModelMapper @Inject constructor() :
    Mapper<List<DrawerItemModel>, List<DrawerRowGroupModel>> {

    override fun map(input: List<DrawerItemModel>) =
        createDrawerRowGroupModels("", input.groupBy { it.parent })

    private fun createDrawerRowGroupModels(
        parent: String,
        drawerItemModelsByParent: Map<String, List<DrawerItemModel>>
    ): List<DrawerRowGroupModel> =
        drawerItemModelsByParent[parent]?.map { hashTagModel ->
            DrawerRowGroupModel(
                drawerRowModel = DrawerRowModel(
                    title = hashTagModel.title,
                    hasChildren = drawerItemModelsByParent[hashTagModel.title]?.isNotEmpty()
                        ?: false,
                    childrenVisible = hashTagModel.expanded,
                    drawableRes = hashTagModel.iconResId,
                    animateArrow = hashTagModel.animateArrow
                ),
                drawerChildrenModel = DrawerChildrenModel(
                    createDrawerRowGroupModels(
                        hashTagModel.title,
                        drawerItemModelsByParent.filterKeys { it != parent }
                    )
                )
            )
        } ?: emptyList()
}