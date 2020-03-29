package com.rgalka88.notethecode.core.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppTransformer : Transformer {

    override val mainScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

    override val ioScheduler: Scheduler
        get() = Schedulers.io()

    override val computationScheduler: Scheduler
        get() = Schedulers.computation()
}