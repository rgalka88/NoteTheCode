package com.rgalka88.notethecode.feature.notelist

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.di.AssistedViewModelFactory
import com.rgalka88.notethecode.core.di.DaggerMvRxViewModelFactory
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.core.rx.Transformer
import com.rgalka88.notethecode.core.rx.applySchedulers
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class NoteListViewModel @AssistedInject constructor(
    @Assisted initialState: NoteListState,
    private val loadCloudSyncStatus: LoadCloudSyncStatus,
    private val rxTransformer: Transformer
) : BaseViewModel<NoteListState>(initialState) {

    @AssistedInject.Factory
    interface Factory : AssistedViewModelFactory<NoteListViewModel, NoteListState> {
        override fun create(initialState: NoteListState): NoteListViewModel
    }

    fun loadTestStatus() {
        loadCloudSyncStatus(UseCase.None())
            .applySchedulers(rxTransformer)
            .execute { copy(testFlag = it() ?: false) }
    }

    companion object :
        DaggerMvRxViewModelFactory<NoteListViewModel, NoteListState>(NoteListViewModel::class.java)
}