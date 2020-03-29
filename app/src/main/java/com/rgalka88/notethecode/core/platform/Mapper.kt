package com.rgalka88.notethecode.core.platform

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}