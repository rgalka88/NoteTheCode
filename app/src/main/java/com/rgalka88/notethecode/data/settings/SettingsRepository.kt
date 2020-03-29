package com.rgalka88.notethecode.data.settings

import android.content.SharedPreferences
import com.rgalka88.notethecode.core.platform.CLOUD_SYNC_ENABLED
import com.rgalka88.notethecode.core.utils.SharedPrefs.get
import com.rgalka88.notethecode.core.utils.SharedPrefs.observe
import com.rgalka88.notethecode.core.utils.SharedPrefs.set
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


class SettingsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun loadCloudStatus(): Observable<Boolean> =
        sharedPreferences.observe(CLOUD_SYNC_ENABLED, false)

    fun toggleCloudSyncState(): Completable = Completable.fromAction {
        val currentState = sharedPreferences[CLOUD_SYNC_ENABLED, false]
        sharedPreferences[CLOUD_SYNC_ENABLED] = currentState.not()
    }
}