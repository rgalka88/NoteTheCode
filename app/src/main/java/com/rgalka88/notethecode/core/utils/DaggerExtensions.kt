package com.rgalka88.notethecode.core.utils

import androidx.fragment.app.FragmentActivity
import com.rgalka88.notethecode.core.di.AppComponent
import com.rgalka88.notethecode.core.platform.NoteTheCodeApp

fun FragmentActivity.appComponent(): AppComponent {
    return (application as NoteTheCodeApp).appComponent
}