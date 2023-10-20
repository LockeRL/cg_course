package com.example.viewer.renderer.scene

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.raytraycer.kdtree.KDTree
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import kotlin.math.PI

class Scene() {
    val objects: MutableList<BaseComplexFigure> = mutableListOf()

    val primitives: List<BasePrimitive>
        get() = objects.fold(listOf()) { acc, fig ->
            acc + fig.parts
        }

    val lights: MutableList<Light> = mutableListOf()

    private val cameras: MutableList<Camera> = mutableListOf()
    val defaultCamera = Camera(Vector3D(0.0, 400.0, 0.0), -PI / 2, 0.0, PI, 300.0)

    var tree: KDTree? = null

    var bgColor = MaterialColor()

    fun addObject(fig: BaseComplexFigure) {
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