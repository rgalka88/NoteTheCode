package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.rgalka88.notethecode.models.DrawerRowGroupModel

data class DrawerState(
    val rowGroupList: Async<List<DrawerRowGroupModel>> = Uninitialized,
    val cloudSyncStatus: Async<Boolean> = Uninitialized
) : MvRxState