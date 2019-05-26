package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.cloud.CloudRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ToggleCloudSync @Inject constructor(
    private val cloudRepository: CloudRepository
) : UseCase<Completable, UseCase.None>() {

    override fun buildUseCase(params: None): Completable = cloudRepository.toggleCloudSyncState()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}