package com.rgalka88.notethecode.feature.drawer.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
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

    @CallbackProp
    fun onRowClickListener(listener: (() -> Unit)?) = listener
        ?.let { setOnClickListener { it() } }

    @CallbackProp
    fun onArrowClickListener(listener: (() -> Unit)?) = listener
        ?.let { arrowIcon.setOnClickListener { it() } }

    @ModelProp
    fun setRow(drawerRowModel: DrawerRowModel) {
        Glide.with(context).load(drawerRowModel.drawableRes).into(rowIcon)
        Glide.with(context).load(R.drawable.ic_arrow).into(arrowIcon)
        rowTitle.text = drawerRowModel.title
    }
}