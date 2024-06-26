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
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

fun convertDateViews(date: Date) : String {
    val simpleFormatView = SimpleDateFormat("EE, MMM dd", Locale.getDefault())
    return simpleFormatView.format(date)
}

fun convertTimeViews(date: Date) : String {
    val simpleTimeView = SimpleDateFormat("hh:mm", Locale.getDefault())
    return simpleTimeView.format(date)
}

fun convertTime(date: Date) : String {
    val simpleTime = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    return simpleTime.format(date)
}

fun pad(input: Int): String {
    val str: String = if (input > 10) {
        input.toString()
    } else {
        "0$input"
    }
    return str
}

fun convertDate(date: Date) : String {
    val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleFormat.format(date)
}

fun convertTimeViews2(dateTime: String) : String {
    val defaultFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val convertDate = SimpleDateFormat("hh:mm", Locale.getDefault())
    var oriDate = Calendar.getInstance().time
    try {
        defaultFormat.parse(dateTime)?.let {
            oriDate = it
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return convertDate.format(oriDate)
}

fun convertDateViews2(dateTime: String) : String {
    val defaultFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val convertDate = SimpleDateFormat("EE, MMM dd", Locale.getDefault())
    var oriDate = Calendar.getInstance().time
    try {
        defaultFormat.parse(dateTime)?.let {
            oriDate = it
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return convertDate.format(oriDate)
}

fun convertDateTimeViews(dateTime: String) : String {
    val defaultFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val convertDate = SimpleDateFormat("EE, MMM dd - HH:mm", Locale.getDefault())
    var oriDate = Calendar.getInstance().time
    try {
        defaultFormat.parse(dateTime)?.let {
            oriDate = it
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return convertDate.format(oriDate)
}

fun convertRp(value: Int): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(value).toString()
}