package com.harsewak.examplesmartrecyclerview.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

fun View.padding(value: Int) {
    setPadding(value, value, value, value)
}


fun  View.basicPadding() : View {
    this.padding(16)
    return this
}

fun TextView.sizeMedium() : TextView {
    this.textSize = 26f
    return this
}

fun TextView.sizeLarge() : TextView {
    this.textSize = 32f
    return this
}

@SuppressLint("ClickableViewAccessibility")
fun TextView.clickEffect(primaryTextColor : Int) : TextView {
    this.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> (view as TextView).setTextColor(Color.parseColor("#4a4a4a"))
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> (view as TextView).setTextColor(primaryTextColor)
        }
        false
    }
    return this
}



