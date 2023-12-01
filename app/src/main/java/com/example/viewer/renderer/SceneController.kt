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
    var mainCamera: Camera = Scene.defaultCamera

    fun changeCameraDist(d: Double) {
        mainCamera.projPlaneDist = d
    }

    fun setBgColor(color: MaterialColor) {
        scene.bgColor = color
    }

    fun rotateFigure(id: Int, ang: Vector3D) {
        scene.rotateObject(id, ang)
        rebuildTree()
    }

    fun moveFigure(id: Int, vec: Vector3D) {
        scene.moveObject(id, vec)
        rebuildTree()
    }

    fun addFigure(fig: BaseComplexFigure) {
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

    fun moveLight(id: Int, vec: Vector3D) {
        scene.moveLight(id, vec)
    }

    fun rotateLight(id: Int, ang: Vector3D) {}

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
        if (id == 0)
            return
        scene.deleteCamera(id)
    }

    fun rotateCamera(id: Int, ang: Vector3D) {
        mainCamera.rotate(ang)
    }

    fun moveCamera(id: Int, vec: Vector3D) {
        mainCamera.move(vec)
    }

    fun clearCameras() {
        scene.clearCameras()
        mainCamera = Scene.defaultCamera
        scene.addCamera(mainCamera)
    }

    fun setMainCamera(id: Int) {
        mainCamera = scene.getCamera(id)
    }

    private fun rebuildTree() {
        render.buildTree(scene)
        println("in controller")
    }
}