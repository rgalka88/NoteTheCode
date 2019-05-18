package com.rgalka88.notethecode

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity
import dagger.android.AndroidInjection

class MainActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
