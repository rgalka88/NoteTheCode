package com.rgalka88.notethecode.models

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.feature.drawer.views.AdapterCallbacks


class DrawerChildrenGroup constructor(
    drawerChildrenModel: DrawerChildrenModel,
    adapterCallbacks: AdapterCallbacks
) : EpoxyModelGroup(R.layout.drawer_children_group,
    buildModels(drawerChildrenModel, adapterCallbacks)
)

fun buildModels(drawerChildrenGroup: DrawerChildrenModel, adapterCallbacks: AdapterCallbacks): List<EpoxyModel<*>> =
    drawerChildrenGroup.childList.map { DrawerRowGroup(it, adapterCallbacks) }