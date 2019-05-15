package com.rgalka88.notethecode.core.platform

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import com.rgalka88.notethecode.core.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class NoteTheCodeApp : Application(), HasActivityInjector, HasSupportFragmentInjector, HasServiceInjector {

    @Inject
    lateinit var activityDaggerInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDaggerInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var serviceDaggerInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    override fun activityInjector() = activityDaggerInjector
    override fun supportFragmentInjector() = fragmentDaggerInjector
    override fun serviceInjector() = serviceDaggerInjector

    private fun initComponent() {
        DaggerAppComponent.factory()
            .create(applicationContext)
            .inject(this)
    }
}