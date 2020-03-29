package com.rgalka88.notethecode.core.utils

import androidx.fragment.app.Fragment
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.feature.drawer.views.AdapterCallbacks
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroup
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel

/**
 * Easily add models to an EpoxyRecyclerView, the same way you would in a buildModels method of EpoxyController.
 * from https://github.com/airbnb/epoxy/wiki/EpoxyRecyclerView#kotlin-extensions
 */

inline fun EpoxyController.drawerGroupRow(
    drawerRowGroupModel: DrawerRowGroupModel,
    adapterCallbacks: AdapterCallbacks,
    modelInitializer: DrawerRowGroup.() -> Unit = {}
) {
    DrawerRowGroup(drawerRowGroupModel, adapterCallbacks).apply {
        modelInitializer()
    }.addTo(this)
}

open class MvRxEpoxyController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

    override fun buildModels() = buildModelsCallback()
}

fun <S : MvRxState, A : BaseViewModel<S>> Fragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = MvRxEpoxyController {
    if (view == null || isRemoving) return@MvRxEpoxyController
    withState(viewModel) { state ->
        buildModels(state)
    }
}