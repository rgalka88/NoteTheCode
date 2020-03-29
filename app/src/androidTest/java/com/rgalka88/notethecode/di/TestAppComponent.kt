package com.rgalka88.notethecode.di

import android.content.Context
import com.rgalka88.notethecode.core.di.AppAssistedModule
import com.rgalka88.notethecode.core.di.AppComponent
import com.rgalka88.notethecode.core.di.BuildersModule
import com.rgalka88.notethecode.core.di.SharedPrefsModule
import com.rgalka88.notethecode.di.module.DrawerTestModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppAssistedModule::class,
        BuildersModule::class,
        TestTransformerModule::class,
        SharedPrefsModule::class,
        DrawerTestModule::class
    ]
)
interface TestAppComponent :AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun drawerTestModule(drawerTestModule: DrawerTestModule): Builder
        fun build(): TestAppComponent
    }
}