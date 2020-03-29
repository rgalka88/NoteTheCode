package com.rgalka88.notethecode.core.rx

import io.reactivex.Scheduler

interface Transformer {

    val mainScheduler: Scheduler
    val ioScheduler: Scheduler
    val computationScheduler: Scheduler
}