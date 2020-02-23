package com.rgalka88.notethecode.models

import androidx.annotation.DrawableRes
import com.rgalka88.notethecode.R

data class DrawerRowModel(
    @DrawableRes val drawableRes: Int = R.drawable.ic_hashtag,
    val title: String,
    val hasChildren: Boolean,
    val childrenVisible: Boolean,
    val animateArrow: Boolean
)