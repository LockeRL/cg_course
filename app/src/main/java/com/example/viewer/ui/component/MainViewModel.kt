package com.example.viewer.ui.component

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import com.example.viewer.renderer.RenderController
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.SceneController
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.complex.RectPyramid
import com.example.viewer.renderer.scene.objects.figure.complex.TriangPyramid
import com.example.viewer.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.PI


class MainViewModel : BaseViewModel() {

    private val _canvas: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val canvas: StateFlow<Bitmap?> = _canvas

    private val sceneController = SceneController
    private val renderController = RenderController

    fun initCanvas(width: Int, height: Int) {
        val canvasWidth = (width * widthPercent).toInt()
        val canvasHeight = (height * heightPercent).toInt()
        _canvas.value = createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
    }

    suspend fun act() {
//        val sphere = Sphere(
//            Vector3D(),
//            300.0,
//            MaterialColor(250, 30, 30),
//            Material(1.0, 5.0, 5.0, 10.0, 10.0)
//        )
//        sceneController.addObject(sphere)

//        val cube = Cube(
//            Vector3D(),
//            400.0,
//            400.0,
//            400.0,
//            MaterialColor(250, 30, 30),
//            Material(4.0, 5.0, 5.0, 3.0, 10.0)
//        )
//        sceneController.addObject(cube)

        val triangPyramid = TriangPyramid(
            Vector3D(),
            600.0,
            400.0,
            20,
            MaterialColor(250, 30, 30),
            Material(4.0, 5.0, 5.0, 3.0, 10.0)
        )
        sceneController.addObject(triangPyramid)


        val rectPyramid = RectPyramid(
            Vector3D(),
            600.0,
            400.0,
            15,
            MaterialColor(250, 30, 30),
            Material(4.0, 5.0, 5.0, 3.0, 10.0)
        )
        sceneController.addObject(rectPyramid)

//        val plane = Plane(
//            Vector3D(),
//            400.0,
//            400.0,
//            MaterialColor(250, 30, 30),
//            Material(1.0, 5.0, 5.0, 10.0, 10.0)
//        )
//        sceneController.addObject(plane)

        val light = Light(Vector3D(800.0, 800.0, 800.0), MaterialColor(255, 255, 255))
        sceneController.addLight(light)
        val camera = Camera(Vector3D(0.0, 600.0, 0.0), -PI / 2, 0.0, PI, 320.0)
//        camera.rotate(z = PI / 4)
        sceneController.addCamera(camera)
        sceneController.setMainCamera(0)
//        controller.setBgColor(MaterialColor(62, 200, 240))
        sceneController.setBgColor(MaterialColor(255, 255, 255))

        render()
    }

    suspend fun rotateCamera() {
        sceneController.rotate()
        render()
    }

    suspend fun moveCamera() {
        sceneController.move()
        render()
    }

    private suspend fun render() {
        val tmp = _canvas.value!!
        renderController.render(tmp, sceneController.mainCamera, sceneController.scene)
        _canvas.value = tmp
    }

    private companion object {
        const val widthPercent = 0.6
        const val heightPercent = 0.9
    }
}
