package com.example.viewer.ui.base

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject

abstract class BaseWindow(context: Context, height: Int, width: Int, addObject: (BaseObject) -> Unit, adapter: ArrayAdapter<String>) {

    protected abstract val layoutId: Int

    private val popupWindow by lazy {
        PopupWindow(
            LayoutInflater.from(context).inflate(layoutId, null),
            width,
            height,
            true
        )
    }

    protected abstract fun createObject(): BaseObject

    val windowView by lazy { popupWindow.contentView!! }

    fun open(v: View) {
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0)
    }

    fun close() {
        popupWindow.dismiss()
    }

    abstract fun applyLogic()

    private fun convertDpToPixels(context: Context, dp: Int) =
        (dp * context.resources.displayMetrics.density).toInt()

}