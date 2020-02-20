package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.cloud.CloudRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoadCloudSyncStatus @Inject constructor(
    private val cloudRepository: CloudRepository
) : UseCase<Observable<Boolean>, UseCase.None>() {

    override fun buildUseCase(params: None): Observable<Boolean> = cloudRepository.loadCloudStatus()
}