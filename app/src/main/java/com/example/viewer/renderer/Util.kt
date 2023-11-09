package com.example.viewer.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.objects.Camera

const val EPS = 1e-5

fun Canvas.fill(col: Int) {
    drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), Paint().apply { color = col })
}

fun Boolean.toInt() = if (this) 1 else 0