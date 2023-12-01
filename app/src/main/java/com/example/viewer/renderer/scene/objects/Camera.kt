package com.example.viewer.renderer.scene.objects

import com.example.viewer.renderer.EPS
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject
import kotlin.math.PI
import kotlin.math.abs
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

    constructor(
        loc: Vector3D,
        xAngle: Double = -PI / 2,
        yAngle: Double = 0.0,
        zAngle: Double,
        dist: Double = 300.0
    ) : this() {
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

    override fun move(vec: Vector3D) {
        val (x, y, z) = vec
        position = position + vec
    }

    override fun rotate(ang: Vector3D) {
        if (abs(ang.x) > EPS) {
            alX += ang.x
            sinAlX = sin(alX)
            cosAlX = cos(alX)
        }

        if (abs(ang.y) > EPS) {
            alY += ang.y
            sinAlY = sin(alY)
            cosAlY = cos(alY)
        }

        if (abs(ang.z) > EPS) {
            alZ += ang.z
            sinAlZ = sin(alZ)
            cosAlZ = cos(alZ)
        }
    }

    fun rotateVec(vec: Vector3D): Vector3D {
        var rVec = Vector3D.rotateVectorX(vec, sinAlX, cosAlX)
        rVec = Vector3D.rotateVectorZ(rVec, sinAlZ, cosAlZ)
        rVec = Vector3D.rotateVectorY(rVec, sinAlY, cosAlY)

        return rVec
    }

}