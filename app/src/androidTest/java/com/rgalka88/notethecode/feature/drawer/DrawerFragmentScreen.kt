package com.rgalka88.notethecode.feature.drawer

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.kaspersky.kaspresso.screens.KScreen
import com.rgalka88.notethecode.R
import org.hamcrest.Matcher


object DrawerFragmentScreen : KScreen<DrawerFragmentScreen>() {

    override val layoutId: Int? = R.layout.fragment_drawer
    override val viewClass: Class<*>? = DrawerFragment::class.java

    val appNameTextView = KTextView { withId(R.id.appNameTextView) }
    val cloudIcon = KImageView { withId(R.id.cloudIcon) }
    val settingsButton = KImageView { withId(R.id.settingsButton) }
    val recyclerView = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = { this.itemType(::Item) })

    class Item(val parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        fun rowIcon(index: Int) =
            KImageView(parent) {
                withIndex(index) {
                    withId(R.id.rowIcon)
                }
            }
        fun title(index: Int) =
            KTextView(parent) {
                withIndex(index) {
                    withId(R.id.rowTitle)
                }
            }

        fun arrowIcon(index: Int) =
            KImageView(parent) {
                withIndex(index) {
                    withId(R.id.arrowIcon)
                }
            }
    }
}