package com.rgalka88.notethecode.data.draweritem

import com.rgalka88.notethecode.R

data class DrawerItemModel(
    val title: String,
    val parent: String = "",
    val iconResId: Int = R.drawable.ic_hashtag,
    val expanded: Boolean = false,
    val animateArrow: Boolean = false
)