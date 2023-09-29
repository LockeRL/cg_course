package com.example.viewer.renderer

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.fill(col: Int) {
    drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), Paint().apply { color = col })
}

fun Boolean.toInt() = if (this) 1 else 0