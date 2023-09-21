package com.example.viewer.renderer.scene.camera

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseUtilObject
import com.example.viewer.renderer.scene.camera.data.CameraAttributes
import com.example.viewer.renderer.scene.camera.data.ViewFrustrum
import kotlin.math.cos
import kotlin.math.sin


class Camera(): BaseUtilObject {
    constructor(attributes: CameraAttributes) : this() {
        setPosition(attributes.position)
        setTop(attributes.top)
        yaw = attributes.yaw
        pitch = attributes.yaw
        setFrustrum(attributes.frustrum)
        updateData()
    }

    override val position = Vector3D()
    val direction: Vector3D = Vector3D()
    val up: Vector3D = Vector3D()
    val right: Vector3D = Vector3D()
    val top: Vector3D = Vector3D()
    var yaw: Double = 0.0
    var pitch: Double = 0.0
    val frustrum: ViewFrustrum = ViewFrustrum()

    fun setPosition(pos: Vector3D) {
        position.set(pos)
    }

    fun setDirection(dir: Vector3D) {
        direction.set(dir)
    }

    fun setUp(upVec: Vector3D) {
        up.set(upVec)
    }

    fun setRight(rightVec: Vector3D) {
        right.set(rightVec)
    }

    fun setTop(topVec: Vector3D) {
        top.set(topVec)
    }

    fun setFrustrum(frus: ViewFrustrum) {
        frustrum.set(frus)
    }

    fun updateData() {
        val yawRad = Math.toRadians(yaw)
        val pitchRad = Math.toRadians(pitch)
        setDirection(
            Vector3D(
                cos(yawRad) * cos(pitchRad),
                sin(pitchRad),
                sin(yawRad) * sin(pitchRad)
            ).apply { normalize() }
        )
        setRight(top.oper(direction).apply { normalize() })
        setUp(direction.oper(right).apply { normalize() })
    }

}