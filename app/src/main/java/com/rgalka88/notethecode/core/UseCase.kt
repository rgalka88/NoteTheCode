package com.rgalka88.notethecode.core

abstract class UseCase<out T, in Params> where T : Any {

    abstract fun buildUseCase(params: Params): T

    operator fun invoke(params: Params): T = buildUseCase(params)

    class None
}