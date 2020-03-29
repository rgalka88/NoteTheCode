package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.data.settings.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import org.junit.Test

class ToggleCloudSyncTest {

    private val repository: SettingsRepository = mockk()
    private val toggleCloudSync = ToggleCloudSync(repository)

    @Test
    fun `invoke calls toggleCloudSyncState on repository`() {
        every { repository.toggleCloudSyncState() } returns Completable.complete()

        toggleCloudSync(UseCase.None())
            .test()
            .assertComplete()

        verify { repository.toggleCloudSyncState() }
    }

    @Test
    fun `invoke returns error when error occurs`() {
        val throwable = Throwable()
        every { repository.toggleCloudSyncState() } returns Completable.error(throwable)

        toggleCloudSync(UseCase.None())
            .test()
            .assertError(throwable)
    }
}