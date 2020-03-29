package com.rgalka88.notethecode

import androidx.test.platform.app.InstrumentationRegistry
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.rgalka88.notethecode.di.DaggerTestAppComponent
import com.rgalka88.notethecode.di.TestAppComponent

abstract class BaseFragmentTest : TestCase() {

    protected fun buildTestAppComponent(block: TestAppComponent.Builder.() -> Unit = {}) =
        with(getApplication()) {
            DaggerTestAppComponent.builder()
                .bindContext(applicationContext)
                .apply(block)
                .build()
                .also { component -> appComponent = component }
                .inject(this)
        }

    protected fun getApplication() = InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .applicationContext as NoteTheCodeTestApp
}