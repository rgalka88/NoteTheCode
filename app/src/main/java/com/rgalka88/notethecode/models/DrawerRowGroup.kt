package com.rgalka88.notethecode.models

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.utils.addTo
import com.rgalka88.notethecode.feature.drawer.views.AdapterCallbacks
import com.rgalka88.notethecode.feature.drawer.views.DrawerRowModelBuilder
import com.rgalka88.notethecode.feature.drawer.views.DrawerRowModel_

data class DrawerRowGroup constructor(
    val drawerRowGroupModel: DrawerRowGroupModel,
    val adapterCallbacks: AdapterCallbacks
) : EpoxyModelGroup(
    R.layout.drawer_row_group,
    buildModels(drawerRowGroupModel, adapterCallbacks)
)

fun buildModels(drawerRowGroupModel: DrawerRowGroupModel, adapterCallbacks: AdapterCallbacks) =
    with(drawerRowGroupModel) {
        mutableListOf<EpoxyModel<*>>().apply {
            drawerRow {
                id(drawerRowModel.title)
                onRowClickListener { }
                onArrowClickListener { adapterCallbacks.onArrowClick(drawerRowModel.title) }
                row(drawerRowModel)
            }

            if (childrenVisible) {
                childrenGroup(drawerChildrenModel, adapterCallbacks) {
                }
            }
        }
    }

private fun MutableList<EpoxyModel<*>>.drawerRow(modelInitializer: DrawerRowModelBuilder.() -> Unit) =
    DrawerRowModel_().apply {
        modelInitializer()
    }.addTo(this)

private fun MutableList<EpoxyModel<*>>.childrenGroup(
    drawerChildrenModel: DrawerChildrenModel,
    adapterCallbacks: AdapterCallbacks,
    initializer: DrawerChildrenGroup.() -> Unit
) {
    if (drawerChildrenModel.childList.isNotEmpty()) {
        DrawerChildrenGroup(drawerChildrenModel, adapterCallbacks)
            .apply { initializer() }
            .addTo(this)
    }
}

