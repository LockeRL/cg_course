package com.example.viewer.renderer.scene.objects

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject

class Light(position: Vector3D, var color: MaterialColor) :
    BaseObject(position) {

    override fun move(vec: Vector3D) {
        position = position + vec
    }

    override fun rotate(ang: Vector3D) {}
}