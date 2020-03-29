package com.rgalka88.notethecode.core.di

import android.content.Context
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.core.platform.NoteTheCodeApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppAssistedModule::class,
        SharedPrefsModule::class,
        BuildersModule::class,
        TransformerModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun viewModelFactories(): Map<Class<out BaseViewModel<*>>, AssistedViewModelFactory<*, *>>
    fun inject(app: NoteTheCodeApp)
}