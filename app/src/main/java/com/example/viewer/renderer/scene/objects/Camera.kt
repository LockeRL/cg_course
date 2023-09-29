package com.example.viewer.renderer.scene.objects

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseObject
import kotlin.math.cos
import kotlin.math.sin

class Camera(
    position: Vector3D = Vector3D(),
    var alX: Double = 0.0,
    var sinAlX: Double = 0.0,
    var cosAlX: Double = 0.0,
    var alY: Double = 0.0,
    var sinAlY: Double = 0.0,
    var cosAlY: Double = 0.0,
    var alZ: Double = 0.0,
    var sinAlZ: Double = 0.0,
    var cosAlZ: Double = 0.0,
    var projPlaneDist: Double = 0.0
) : BaseObject(position) {

    constructor(loc: Vector3D, xAngle: Double, yAngle: Double, zAngle: Double, dist: Double): this() {
        position = loc
        alX = xAngle
        sinAlX = sin(xAngle)
        cosAlX = cos(xAngle)
        alY = yAngle
        sinAlY = sin(yAngle)
        cosAlY = cos(yAngle)
        alZ = zAngle
        sinAlZ = sin(zAngle)
        cosAlZ = cos(zAngle)
        projPlaneDist = dist
    }
}