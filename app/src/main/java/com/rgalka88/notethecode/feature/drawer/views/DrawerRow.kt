package com.rgalka88.notethecode.feature.drawer.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.models.DrawerRowModel
import kotlinx.android.synthetic.main.drawer_row.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DrawerRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.drawer_row, this, true)
    }

    @ModelProp
    fun setDrawerRow(drawerRowModel: DrawerRowModel) {
        Glide.with(context).load(drawerRowModel.drawableRes).into(rowIcon)
        rowTitle.text = drawerRowModel.title
    }
}