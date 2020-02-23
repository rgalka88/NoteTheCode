package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ToggleHashTag @Inject constructor(
    private val setHashTagExpand: SetHashTagExpand,
    private val setAnimationEnded: SetAnimationEnded
) : UseCase<Completable, String>() {

    override fun buildUseCase(params: String): Completable =
        setHashTagExpand(params).toSingleDefault(params)
            .delay(200, TimeUnit.MILLISECONDS)
            .flatMapCompletable { setAnimationEnded(params) }
}