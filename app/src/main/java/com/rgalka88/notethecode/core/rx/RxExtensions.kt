package com.rgalka88.notethecode.core.rx

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.applySchedulers(transformer: Transformer): Observable<T> =
    subscribeOn(transformer.ioScheduler)
        .observeOn(transformer.mainScheduler)

fun <T> Single<T>.applySchedulers(transformer: Transformer): Single<T> =
    subscribeOn(transformer.ioScheduler)
        .observeOn(transformer.mainScheduler)

fun <T> Maybe<T>.applySchedulers(transformer: Transformer): Single<T> =
    subscribeOn(transformer.ioScheduler)
        .observeOn(transformer.mainScheduler)
        .toSingle()

fun Completable.applySchedulers(transformer: Transformer): Completable =
    subscribeOn(transformer.ioScheduler)
        .observeOn(transformer.mainScheduler)