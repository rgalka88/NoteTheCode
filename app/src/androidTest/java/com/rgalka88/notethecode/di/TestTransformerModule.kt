package com.rgalka88.notethecode.di

import com.rgalka88.notethecode.TestTransformer
import com.rgalka88.notethecode.core.rx.Transformer
import dagger.Module
import dagger.Provides


@Module
class TestTransformerModule {

    @Provides
    fun provideTestTransformer(): Transformer = TestTransformer()
}