package com.example.viewer.renderer.scene.objects.figure.base

import com.example.viewer.renderer.math.Vector3D

abstract class BaseObject(var position: Vector3D) {

    abstract fun move(vec: Vector3D)

    abstract fun rotate(ang: Vector3D)
}