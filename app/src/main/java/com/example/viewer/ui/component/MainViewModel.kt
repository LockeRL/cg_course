package com.example.viewer.ui.component

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewModelScope
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes
import com.example.viewer.renderer.scene.render.RenderWidget
import com.example.viewer.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    private val _canvas: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val canvas: StateFlow<Bitmap?> = _canvas

    fun initCanvas(width: Int, height: Int) {
        val canvasWidth = (width * widthPercent).toInt()
        val canvasHeight = (height * heightPercent).toInt()
        _canvas.value = createBitmap(canvasWidth, canvasHeight)
    }

    fun change() {
        val buf = _canvas.value
        buf?.setPixel(
            0, 0, Color.argb(
                (0..255).random(),
                (0..255).random(),
                (0..255).random(),
                (0..255).random()
            )
        )
        _canvas.value = buf
    }

    fun act() {
        val buf = _canvas.value!!
        val renderWidget = RenderWidget(canvas.value!!.width, canvas.value!!.height, buf)
        renderWidget.addModel(ModelAttributes(0.0, 50.0, 300.0, 6),
            Material(MaterialColor(66, 255, 0), MaterialColor(255, 255, 255), 256))
        renderWidget.test()
        _canvas.value = buf
    }

    private companion object {
        const val widthPercent = 0.6
        const val heightPercent = 0.9
    }
}
