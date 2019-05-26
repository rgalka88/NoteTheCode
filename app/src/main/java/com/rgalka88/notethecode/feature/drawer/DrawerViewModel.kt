package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rgalka88.notethecode.core.UseCase.None
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleCloudSync
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DrawerViewModel @AssistedInject constructor(
    @Assisted initialState: DrawerState,
    loadCloudSyncStatus: LoadCloudSyncStatus,
    private val toggleCloudSync: ToggleCloudSync
) : BaseViewModel<DrawerState>(initialState) {

    init {
        loadCloudSyncStatus(None()).execute { copy(cloudSyncStatus = it) }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: DrawerState): DrawerViewModel
    }

    companion object : MvRxViewModelFactory<DrawerViewModel, DrawerState> {
        override fun create(viewModelContext: ViewModelContext, state: DrawerState): DrawerViewModel? {
            val fragment: DrawerFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.draweViewModelFactory.create(state)
        }
    }

    fun toggleCloudSync() {
        toggleCloudSync(None()).execute { this }
    }
}