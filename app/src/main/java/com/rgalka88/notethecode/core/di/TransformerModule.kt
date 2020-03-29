package com.rgalka88.notethecode.core.di

import com.rgalka88.notethecode.core.rx.AppTransformer
import com.rgalka88.notethecode.core.rx.Transformer
import dagger.Module
import dagger.Provides

@Module
class TransformerModule {

    @Provides
    fun provideTransformer(): Transformer = AppTransformer()
}