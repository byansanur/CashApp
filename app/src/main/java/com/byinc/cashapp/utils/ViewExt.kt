package com.byinc.cashapp.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.setImageDrawableRes(@DrawableRes img: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, img))
}