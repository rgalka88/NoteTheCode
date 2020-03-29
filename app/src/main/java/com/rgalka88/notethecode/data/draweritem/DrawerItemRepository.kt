package com.rgalka88.notethecode.data.draweritem

import com.rgalka88.notethecode.data.Repository
import javax.inject.Inject

class DrawerItemRepository @Inject constructor(
    localDrawerItemDataSource: LocalDrawerItemDataSource
) : Repository<DrawerItemModel>(localDrawerItemDataSource)