package com.example.viewer.renderer.scene

import com.example.viewer.renderer.scene.base.BaseFigure
import com.example.viewer.renderer.scene.kdtree.KDTree
import com.example.viewer.renderer.scene.kdtree.Voxel
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light

class Scene() {
    val objects: MutableList<BaseFigure> = mutableListOf()
    val lights: MutableList<Light> = mutableListOf()
    val cameras: MutableList<Camera> = mutableListOf()

    var tree: KDTree? = null

    var bgColor = MaterialColor()

    fun addObject(fig: BaseFigure) {
        objects.add(fig)
    }

    fun deleteObject(id: Int) {
        objects.removeAt(id)
    }

    fun clearObjects() {
        objects.clear()
    }

    fun addLight(light: Light) {
        lights.add(light)
    }

    fun deleteLight(id: Int) {
        lights.removeAt(id)
    }

    fun clearLights() {
        lights.clear()
    }

    fun addCamera(camera: Camera) {
        cameras.add(camera)
    }

    fun deleteCamera(id: Int) {
        cameras.removeAt(id)
    }

    fun clearCameras() {
        cameras.clear()
    }

    fun getCamera(id: Int) = cameras[id]

}