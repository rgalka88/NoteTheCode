package com.rgalka88.notethecode

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.mvrx.BaseMvRxActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvRxActivity() {

    private val navController: NavController
        get() = findNavController(R.id.navHost)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId ?: 0) {
        android.R.id.home -> {
            drawer.openDrawer(GravityCompat.START)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
