package com.example.viewer.renderer.scene.camera.data

data class ViewFrustrum(
    var fov: Double = 0.0,
    var nearPlane: Double = 0.0,
    var farPlane: Double = 0.0,
    var aspectRatio: Double = 0.0
) {
    fun set(other: ViewFrustrum) {
        fov = other.fov
        nearPlane = other.nearPlane
        farPlane = other.farPlane
        aspectRatio = other.aspectRatio
    }
}
