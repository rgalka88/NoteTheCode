package com.rgalka88.notethecode.core.platform

import android.app.Application
import com.rgalka88.notethecode.core.di.AppComponent
import com.rgalka88.notethecode.core.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class NoteTheCodeApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    override fun androidInjector() = dispatchingAndroidInjector

    private fun initComponent() = DaggerAppComponent
        .factory()
        .create(applicationContext = applicationContext)
        .also { component -> appComponent = component }
        .inject(this)
}