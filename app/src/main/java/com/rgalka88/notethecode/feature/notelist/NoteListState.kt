package com.rgalka88.notethecode.feature.notelist

import com.airbnb.mvrx.MvRxState

data class NoteListState(val text: String = "Hello World") : MvRxState