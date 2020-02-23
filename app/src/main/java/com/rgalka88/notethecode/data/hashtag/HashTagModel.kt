package com.rgalka88.notethecode.data.hashtag

import com.rgalka88.notethecode.R

data class HashTagModel(
    val title: String,
    val parent: String = "",
    val iconResId: Int = R.drawable.ic_note,
    val expanded: Boolean = false,
    val animateArrow: Boolean = false
)