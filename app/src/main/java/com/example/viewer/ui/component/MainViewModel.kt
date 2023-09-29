package com.example.viewer.ui.component

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.Controller
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.Sphere
import com.example.viewer.renderer.scene.objects.figure.Triangle
import com.example.viewer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainViewModel : BaseViewModel() {

    private val _canvas: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val canvas: StateFlow<Bitmap?> = _canvas

    private val controller = Controller()

    fun initCanvas(width: Int, height: Int) {
        val canvasWidth = (width * widthPercent).toInt()
        val canvasHeight = (height * heightPercent).toInt()
//        _canvas.value = createBitmap(5, 5)
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

    suspend fun act() {
        val sphere = Sphere(
            300.0,
            Vector3D(),
            MaterialColor(250, 30, 30),
            Material(1.0, 5.0, 5.0, 10.0, 0.0, 10.0)
        )
        controller.addObject(sphere)
        val triangle = Triangle(
            Vector3D(-700.0, -700.0, -130.0),
            Vector3D(700.0, -700.0, -130.0),
            Vector3D(0.0, 400.0, -130.0),
            MaterialColor(100, 255, 30),
            Material(1.0, 6.0, 0.0, 2.0, 0.0, 0.0)
        )
        controller.addObject(triangle)
        val light = Light(Vector3D(-300.0, 300.0, 300.0), MaterialColor(255, 255, 255))
        controller.addLight(light)
        val camera = Camera(Vector3D(0.0, 500.0, 0.0), -1.57, 0.0, 3.14, 320.0)
        controller.addCamera(camera)
        controller.setBgColor(MaterialColor(200, 200, 50))
//        controller.setBgColor(MaterialColor(255, 255, 255))

        val tmp = _canvas.value!!
        controller.renderScene(tmp)
        _canvas.value = tmp
    }

    private companion object {
        const val widthPercent = 0.6
        const val heightPercent = 0.9
    }
}
