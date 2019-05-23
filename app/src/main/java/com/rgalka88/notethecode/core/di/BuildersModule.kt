package com.rgalka88.notethecode.core.di


import com.rgalka88.notethecode.MainActivity
import com.rgalka88.notethecode.feature.drawer.DrawerFragment
import com.rgalka88.notethecode.feature.notelist.NoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindNoteListFragment(): NoteListFragment

    @ContributesAndroidInjector
    abstract fun bindDrawerFragment(): DrawerFragment
}