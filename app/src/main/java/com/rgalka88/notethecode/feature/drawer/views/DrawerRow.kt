package com.rgalka88.notethecode.feature.drawer.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.RotateAnimation
import android.view.animation.RotateAnimation.RELATIVE_TO_SELF
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
    fun setRow(model: DrawerRowModel) {
        Glide.with(context).load(model.drawableRes).into(rowIcon)
        rowTitle.text = model.title
        if (model.hasChildren) renderArrow(model.childrenVisible, model.animateArrow)
        else arrowIcon.visibility = View.GONE
    }

    private fun renderArrow(childrenVisible: Boolean, animateArrow: Boolean) {
        arrowIcon.visibility = View.VISIBLE
        when (animateArrow) {
            true -> renderArrowWithAnimation(childrenVisible)
            false -> renderArrowWithoutAnimation(childrenVisible)
        }
    }

    private fun renderArrowWithoutAnimation(childrenVisible: Boolean) = when (childrenVisible) {
        true -> arrowIcon.startAnimation(getRotateAnimation(90f, 90f, 0))
        false -> arrowIcon.startAnimation(getRotateAnimation(0f, 0f, 0))
    }

    private fun renderArrowWithAnimation(childrenVisible: Boolean) = when (childrenVisible) {
        true -> arrowIcon.startAnimation(getRotateAnimation(0f, 90f, 200))
        false -> arrowIcon.startAnimation(getRotateAnimation(90f, 0f, 200))
    }

    private fun getRotateAnimation(from: Float, to: Float, animDuration: Long) =
        RotateAnimation(from, to, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f).apply {
            fillAfter = true
            duration = animDuration
        }
}