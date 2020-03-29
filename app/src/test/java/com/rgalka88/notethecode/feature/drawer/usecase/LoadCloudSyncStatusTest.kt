package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.settings.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test

class LoadCloudSyncStatusTest {

    private val settingsRepository: SettingsRepository = mockk()
    private val loadCloudSyncStatus = LoadCloudSyncStatus(settingsRepository)

    @Test
    fun `invoke returns expected status when sync is disabled`() {
        every { settingsRepository.loadCloudStatus() } returns Observable.just(false)

        loadCloudSyncStatus(UseCase.None())
            .test()
            .assertValue(false)
    }

    @Test
    fun `invoke returns expected status when sync is enabled`() {
        every { settingsRepository.loadCloudStatus() } returns Observable.just(true)

        loadCloudSyncStatus(UseCase.None())
            .test()
            .assertValue(true)
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val throwable = Throwable()
        every { settingsRepository.loadCloudStatus() } returns Observable.error(throwable)

        loadCloudSyncStatus(UseCase.None())
            .test()
            .assertError(throwable)
    }
}