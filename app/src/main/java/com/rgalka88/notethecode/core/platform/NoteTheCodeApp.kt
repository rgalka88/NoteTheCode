package com.rgalka88.notethecode.core.platform

import android.app.Application
import com.rgalka88.notethecode.core.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class NoteTheCodeApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    override fun androidInjector() = dispatchingAndroidInjector

    private fun initComponent() {
        DaggerAppComponent.factory()
            .create(applicationContext)
            .inject(this)
    }
}