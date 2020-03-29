package com.rgalka88.notethecode.feature.drawer

import com.rgalka88.notethecode.core.UseCase.None
import com.rgalka88.notethecode.core.di.AssistedViewModelFactory
import com.rgalka88.notethecode.core.di.DaggerMvRxViewModelFactory
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.core.rx.Transformer
import com.rgalka88.notethecode.core.rx.applySchedulers
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.LoadDrawerItems
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleCloudSync
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleDrawerItem
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DrawerViewModel @AssistedInject constructor(
    @Assisted initialState: DrawerState,
    private val loadCloudSyncStatus: LoadCloudSyncStatus,
    private val toggleCloudSync: ToggleCloudSync,
    private val loadDrawerItems: LoadDrawerItems,
    private val toggleDrawerItem: ToggleDrawerItem,
    private val rxTransformer: Transformer
) : BaseViewModel<DrawerState>(initialState) {

    init {
        observeDrawerItems()
        observeCloudSyncStatus()
    }

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<DrawerViewModel, DrawerState> {
        override fun create(initialState: DrawerState): DrawerViewModel
    }

    fun toggleCloudSync() {
        toggleCloudSync(None())
            .applySchedulers(rxTransformer)
            .execute { this }
    }

    fun toggleRowExpand(itemId: String) {
        toggleDrawerItem(itemId)
            .applySchedulers(rxTransformer)
            .execute { this }
    }

    private fun observeDrawerItems() {
        loadDrawerItems(None())
            .applySchedulers(rxTransformer)
            .execute { copy(rowGroupList = it) }
    }

    private fun observeCloudSyncStatus() {
        loadCloudSyncStatus(None())
            .applySchedulers(rxTransformer)
            .execute { copy(cloudSyncStatus = it) }
    }

    companion object :
        DaggerMvRxViewModelFactory<DrawerViewModel, DrawerState>(DrawerViewModel::class.java)
}