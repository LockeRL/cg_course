package com.example.viewer.renderer.scene.camera

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.camera.data.CameraMovement
import com.example.viewer.renderer.scene.camera.data.CameraRotation
import kotlin.math.tan

class CameraManager(var speed: Double = 0.0, var sensitivity: Double = 0.0) {
    fun rotate(camera: Camera, rotation: CameraRotation) {
        var yaw = camera.yaw
        var pitch = camera.pitch
        yaw += rotation.dYaw * sensitivity
        pitch += rotation.dPitch * sensitivity
        if (pitch >= 90.0)
            pitch = 90.0
        if (pitch <= -90.0)
            pitch = -90.0
        if (yaw >= 180.0)
            yaw = -180.0
        if (yaw <= -180.0)
            yaw = 180.0

        camera.yaw = yaw
        camera.pitch = pitch
        camera.updateData()
    }

    fun move(camera: Camera, movement: CameraMovement) {
        val pos = Vector3D().apply { set(camera.position) }
        val dir = camera.direction
        val right = camera.right
        if (movement.forward)
            pos += dir * speed
        if (movement.backward)
            pos -= dir * speed
        if (movement.left)
            pos -= right * speed
        if (movement.right)
            pos += right * speed
        camera.setPosition(pos)
    }

    fun getLookAt(camera: Camera): Matrix {
        val translate = Matrix.identityMatrix(4)
        val pos = camera.position
        for (i in 0 until 3)
            translate[3, i] = -pos[i]

        return translate * getRotation(camera)
    }

    fun getRotation(camera: Camera): Matrix {
        val rotate = Matrix(4, 4)
        val dir = camera.direction
        val up = camera.up
        val right = camera.right
        rotate[3, 3] = 1.0
        for (i in 0 until 3) {
            rotate[i, 0] = right[i]
            rotate[i, 1] = up[i]
            rotate[i, 2] = dir[i]
        }
        return rotate
    }

    fun getProjection(camera: Camera): Matrix {
        val frustrum = camera.frustrum
        val projection = Matrix(4, 4)
        projection[1, 1] = 1 / tan(Math.toRadians(frustrum.fov / 2))
        projection[0, 0] = projection[1, 1] / frustrum.aspectRatio
        val planeDiff = frustrum.farPlane - frustrum.nearPlane
        projection[2, 2] =
            (frustrum.farPlane + frustrum.nearPlane) / planeDiff
        projection[3, 2] = -2.0 * frustrum.nearPlane * frustrum.farPlane / planeDiff
        projection[2, 3] = 1.0
        return projection
    }

}