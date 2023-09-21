package com.example.viewer.renderer.scene.camera.data

import com.example.viewer.renderer.math.Vector3D

data class CameraAttributes(
    var position: Vector3D = Vector3D(),
    var yaw: Double = 0.0,
    var pitch: Double = 0.0,
    var top: Vector3D = Vector3D(),
    var frustrum: ViewFrustrum = ViewFrustrum()
)
