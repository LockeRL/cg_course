package com.example.viewer.renderer.scene.base

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.Material
import com.example.viewer.renderer.scene.MaterialColor

abstract class BaseFigure(position: Vector3D, var color: MaterialColor, var material: Material) :
    BaseObject(position) {

    abstract fun intersect(vecStart: Vector3D, vec: Vector3D): Vector3D?
    abstract fun getMinBoundaryPoint(): Vector3D
    abstract fun getMaxBoundaryPoint(): Vector3D
    abstract fun getNormalVector(point: Vector3D): Vector3D
}