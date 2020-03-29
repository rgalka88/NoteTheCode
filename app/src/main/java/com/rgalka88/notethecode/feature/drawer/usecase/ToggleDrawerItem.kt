package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ToggleDrawerItem @Inject constructor(
    private val setDrawerItemExpand: SetDrawerItemExpand,
    private val setAnimationEnded: SetAnimationEnded
) : UseCase<Completable, String>() {

    override fun buildUseCase(params: String): Completable =
        setDrawerItemExpand(params).toSingleDefault(params)
            .delay(200, TimeUnit.MILLISECONDS)
            .flatMapCompletable { setAnimationEnded(params) }
}