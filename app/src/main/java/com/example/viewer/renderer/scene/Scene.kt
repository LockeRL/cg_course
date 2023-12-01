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
    private val objects: MutableList<BaseComplexFigure> = mutableListOf()
    val objectsNum: Int
        get() = objects.size

    val primitives: List<BasePrimitive>
        get() = objects.fold(listOf()) { acc, fig ->
            acc + fig.parts
        }

    val lights: MutableList<Light> = mutableListOf()
    val lightsNum: Int
        get() = lights.size

    private val cameras: MutableList<Camera> = mutableListOf()
    val camerasNum: Int
        get() = cameras.size

    var tree: KDTree? = null

    var bgColor = MaterialColor(255, 255, 255)


    fun addObject(fig: BaseComplexFigure) {
        objects.add(fig)
    }

    fun deleteObject(id: Int) {
        objects.removeAt(id)
    }

    fun clearObjects() {
        objects.clear()
    }

    fun moveObject(id: Int, vec: Vector3D) {
        objects[id].move(vec)
    }

    fun rotateObject(id: Int, ang: Vector3D) {
        objects[id].rotate(ang)
    }

    fun addLight(light: Light) {
        lights.add(light)
    }

    fun deleteLight(id: Int) {
        lights.removeAt(id)
    }

    fun moveLight(id: Int, vec: Vector3D) {
        lights[id].move(vec)
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

    companion object {
        val defaultCamera = Camera(Vector3D(600.0, 0.0, 0.0), -PI / 2, 0.0, PI / 2, 320.0)
    }

}