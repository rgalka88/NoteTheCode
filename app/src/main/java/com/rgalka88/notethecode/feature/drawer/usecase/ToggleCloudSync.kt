package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.settings.SettingsRepository
import io.reactivex.Completable
import javax.inject.Inject

class ToggleCloudSync @Inject constructor(
    private val settingsRepository: SettingsRepository
) : UseCase<Completable, UseCase.None>() {

    override fun buildUseCase(params: None): Completable = settingsRepository.toggleCloudSyncState()
}