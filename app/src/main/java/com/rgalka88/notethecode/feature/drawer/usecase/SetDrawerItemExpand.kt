package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.utils.takeOnlyFirst
import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetDrawerItemExpand @Inject constructor(
    private val drawerItemRepository: DrawerItemRepository
) : UseCase<Completable, String>() {

    override fun buildUseCase(params: String): Completable = drawerItemRepository
        .query()
        .where("title", params)
        .findFirst()
        .takeOnlyFirst()
        .switchMapCompletable { hashTag -> saveHashTag(hashTag) }

    private fun saveHashTag(drawerItem: DrawerItemModel): Completable =
        drawerItemRepository.save(
            drawerItem.copy(
                expanded = drawerItem.expanded.not(),
                animateArrow = true
            )
        )
}