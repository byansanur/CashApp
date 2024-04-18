package com.byinc.cashapp.utils

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.byinc.cashapp.databinding.DialogLoadingBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
fun showDialogLoadingLogo(context: Context, layoutInflater: LayoutInflater) {
    val binding = DialogLoadingBinding.inflate(layoutInflater)
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCanceledOnTouchOutside(false)
    dialog.setContentView(binding.root)
    playAnimation(binding.ivLogo)
    dialog.show()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    GlobalScope.launch {
        delay(2000L)
        dialog.dismiss()
    }
}

private fun playAnimation(target: View) {
    ObjectAnimator.ofFloat(target, View.TRANSLATION_X, -30f, 30f).apply {
        duration = 1000
        repeatCount = ObjectAnimator.INFINITE
        repeatMode = ObjectAnimator.REVERSE
    }.start()
}