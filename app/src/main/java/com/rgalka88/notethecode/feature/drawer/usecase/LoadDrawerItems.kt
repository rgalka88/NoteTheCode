package com.rgalka88.notethecode.feature.drawer.usecase


import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.UseCase.None
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import com.rgalka88.notethecode.feature.drawer.mapper.DrawerItemToDrawerRowGroupModelMapper
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import io.reactivex.Observable
import javax.inject.Inject

class LoadDrawerItems @Inject constructor(
    private val drawerItemRepository: DrawerItemRepository,
    private val mapper: DrawerItemToDrawerRowGroupModelMapper
) : UseCase<Observable<List<DrawerRowGroupModel>>, None>() {

    override fun buildUseCase(params: None): Observable<List<DrawerRowGroupModel>> =
        drawerItemRepository.getAll().map(mapper::map)
}