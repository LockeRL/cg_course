package com.example.viewer.renderer.scene.objects.figure.base

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor

abstract class BasePrimitive(val position: Vector3D, var color: MaterialColor, var material: Material) {
    abstract fun intersect(vecStart: Vector3D, vec: Vector3D): Vector3D?
    abstract fun getMinBoundaryPoint(): Vector3D
    abstract fun getMaxBoundaryPoint(): Vector3D
    abstract fun getNormalVector(point: Vector3D): Vector3D
    fun move(vec: Vector3D) {
        position += vec
    }
    abstract fun rotate(ang: Vector3D)
}