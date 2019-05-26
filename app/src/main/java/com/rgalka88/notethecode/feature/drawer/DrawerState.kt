package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class DrawerState(
    val hashTagsList: Async<List<String>> = Uninitialized,
    val cloudSyncStatus: Async<Boolean> = Uninitialized
) : MvRxState