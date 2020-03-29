package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.utils.takeOnlyFirst
import com.rgalka88.notethecode.data.draweritem.DrawerItemModel
import com.rgalka88.notethecode.data.draweritem.DrawerItemRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetAnimationEnded @Inject constructor(
    private val drawerItemRepository: DrawerItemRepository
) : UseCase<Completable, String>() {
    override fun buildUseCase(params: String): Completable = drawerItemRepository
        .query()
        .where("title", params)
        .findFirst()
        .takeOnlyFirst()
        .flatMapCompletable { hashTag -> animationHasEnded(hashTag) }

    private fun animationHasEnded(drawerItem: DrawerItemModel): Completable =
        drawerItemRepository.save(
            drawerItem.copy(
                animateArrow = false
            )
        )
}