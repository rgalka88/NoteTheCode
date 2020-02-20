@file:Suppress("unused")

package com.rgalka88.notethecode.core.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import io.reactivex.Observable

fun <T : Any> T.addTo(mutableList: MutableList<T>) = mutableList.add(this)

fun <T> Observable<T>.takeOnlyFirst(): Observable<T> = take(1)

fun <T> List<T>.replace(newValue: T, block: (T) -> Boolean): List<T> =
    map { if (block(it)) newValue else it }

fun <T> List<T>.addOrRemoveIfExist(value: T): List<T> = with(toMutableList()) {
    if (contains(value)) apply { remove(value) }
    else apply { add(value) }
}

fun <T> List<T>.addOrReplaceIfExist(value: T, block: (T) -> Boolean): List<T> =
    if (find { block(it) } == null) toMutableList().apply { add(value) }
    else replace(value) { block(it) }

fun AppCompatActivity.findNavController(@IdRes viewId: Int): NavController =
    (supportFragmentManager.findFragmentById(viewId) as NavHostFragment).navController