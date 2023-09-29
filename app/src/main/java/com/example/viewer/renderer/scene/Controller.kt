package com.example.viewer.renderer.scene

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.viewer.renderer.scene.base.BaseFigure
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light

class Controller() {
    private val scene = Scene()
    private val render = Render
    private var mainCamera: Camera? = null

    fun renderScene(canvas: Bitmap) {
        println("in renderScene controller")
        render.renderScene(scene, mainCamera!!, canvas)
    }

    fun setBgColor(color: MaterialColor) {
        scene.bgColor = color
    }

    fun addObject(fig: BaseFigure) {
        scene.addObject(fig)
        rebuildTree()
    }

    fun deleteObject(id: Int) {
        scene.deleteObject(id)
        rebuildTree()
    }

    fun clearObjects() {
        scene.clearObjects()
        rebuildTree()
    }

    fun addLight(light: Light) {
        scene.addLight(light)
    }

    fun deleteLight(id: Int) {
        scene.deleteLight(id)
    }

    fun clearLights() {
        scene.clearLights()
    }

    fun addCamera(camera: Camera) {
        if (scene.cameras.isEmpty())
            mainCamera = camera
        scene.addCamera(camera)
    }

    fun deleteCamera(id: Int) {
        if (scene.getCamera(id) == mainCamera)
            mainCamera = null
        scene.deleteCamera(id)
    }

    fun clearCameras() {
        scene.clearCameras()
        mainCamera = null
    }

    fun setMainCamera(id: Int) {
        mainCamera = scene.getCamera(id)
    }

    private fun rebuildTree() {
        render.buildTree(scene)
        println("in controller")
    }
}