package com.example.viewer.renderer.scene.camera.data

data class CameraMovement(
    var forward: Boolean = false,
    var backward: Boolean = false,
    var left: Boolean = false,
    var right: Boolean = false
)
