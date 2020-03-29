package com.rgalka88.notethecode.core.di

import com.rgalka88.notethecode.feature.drawer.DrawerViewModel
import com.rgalka88.notethecode.feature.notelist.NoteListViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_AppAssistedModule::class])
interface AppAssistedModule {

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    fun noteListViewModelFactory(factory: NoteListViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(DrawerViewModel::class)
    fun drawerViewModelFactory(factory: DrawerViewModel.Factory): AssistedViewModelFactory<*, *>
}
