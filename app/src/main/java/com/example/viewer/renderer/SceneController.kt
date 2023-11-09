package com.example.viewer.renderer

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.raytraycer.Render

object SceneController {
    val scene = Scene()
    private val render = Render
    var mainCamera: Camera = scene.defaultCamera

    fun rotate() {
        mainCamera.rotate(z = -0.5)
    }

    fun move() {
        mainCamera.move(Vector3D(-100.0))
    }

    fun setBgColor(color: MaterialColor) {
        scene.bgColor = color
    }

    fun rotateObject(id: Int, ang: Vector3D) {

        rebuildTree()
    }

    fun moveObject(id: Int, vec: Vector3D) {

        rebuildTree()
    }

    fun addObject(fig: BaseComplexFigure) {
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
        scene.addCamera(camera)
    }

    fun deleteCamera(id: Int) {
        scene.deleteCamera(id)
    }

    fun clearCameras() {
        scene.clearCameras()
        mainCamera = scene.defaultCamera
    }

    fun setMainCamera(id: Int) {
        mainCamera = scene.getCamera(id)
    }

    private fun rebuildTree() {
        render.buildTree(scene)
        println("in controller")
    }
}