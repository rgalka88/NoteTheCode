package com.rgalka88.notethecode.core.di

import androidx.fragment.app.FragmentActivity
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.core.utils.appComponent

abstract class DaggerMvRxViewModelFactory<VM : BaseViewModel<S>, S : MvRxState>(
    private val viewModelClass: Class<out BaseViewModel<S>>
) : MvRxViewModelFactory<VM, S> {

    override fun create(viewModelContext: ViewModelContext, state: S): VM? {
        return createViewModel(viewModelContext.activity, state)
    }

    private fun <VM : BaseViewModel<S>, S : MvRxState> createViewModel(fragmentActivity: FragmentActivity, state: S): VM {
        val viewModelFactoryMap = fragmentActivity.appComponent().viewModelFactories()
        val viewModelFactory = viewModelFactoryMap[viewModelClass]
        @Suppress("UNCHECKED_CAST")
        val castedViewModelFactory = viewModelFactory as? AssistedViewModelFactory<VM, S>
        val viewModel = castedViewModelFactory?.create(state)
        return viewModel as VM
    }
}