package com.rgalka88.notethecode.core.di

import com.airbnb.mvrx.MvRxState
import com.rgalka88.notethecode.core.platform.BaseViewModel

interface AssistedViewModelFactory<VM : BaseViewModel<S>, S : MvRxState> {
    fun create(initialState: S): VM
}