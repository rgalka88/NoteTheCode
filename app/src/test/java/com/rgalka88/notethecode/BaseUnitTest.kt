@file:Suppress("MemberVisibilityCanBePrivate")

package com.rgalka88.notethecode

import com.rgalka88.notethecode.core.rx.Transformer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins

abstract class BaseUnitTest {
    init {
        overrideSchedulers()
    }

    protected fun overrideSchedulers(transformer: Transformer = UnitTestTransformer()) {
        RxAndroidPlugins.reset()
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setMainThreadSchedulerHandler { transformer.mainScheduler }
        RxJavaPlugins.setIoSchedulerHandler { transformer.ioScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { transformer.computationScheduler }
    }
}