package com.rgalka88.notethecode.core.di

import android.content.Context
import com.rgalka88.notethecode.core.utils.SharedPrefs
import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule {

    @Provides
    fun provideSharedPrefs(applicationContext: Context) = SharedPrefs.defaultPrefs(applicationContext)
}