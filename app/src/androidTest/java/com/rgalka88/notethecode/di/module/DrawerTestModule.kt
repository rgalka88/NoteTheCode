package com.rgalka88.notethecode.di.module

import com.rgalka88.notethecode.data.settings.SettingsRepository
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import com.rgalka88.notethecode.feature.drawer.mapper.DrawerItemToDrawerRowGroupModelMapper
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.LoadDrawerItems
import dagger.Module
import dagger.Provides

@Module
class DrawerTestModule(
    private val loadCloudSyncStatus: LoadCloudSyncStatus? = null,
    private val loadDrawerItems: LoadDrawerItems? = null
) {

    @Provides
    fun bindLoadCloudStatus(repository: SettingsRepository): LoadCloudSyncStatus =
        loadCloudSyncStatus ?: LoadCloudSyncStatus(repository)

    @Provides
    fun bindLoadDrawerItems(
        repository: DrawerItemRepository,
        mapper: DrawerItemToDrawerRowGroupModelMapper
    ): LoadDrawerItems = loadDrawerItems ?: LoadDrawerItems(repository, mapper)
}