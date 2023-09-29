package com.example.viewer.renderer.scene.kdtree

data class Voxel(
    var xMin: Double = 0.0,
    var yMin: Double = 0.0,
    var zMin: Double = 0.0,
    var xMax: Double = 0.0,
    var yMax: Double = 0.0,
    var zMax: Double = 0.0
) {
    fun set(other: Voxel) {
        xMin = other.xMin
        yMin = other.yMin
        zMin = other.zMin
        zMax = other.zMax
        yMax = other.yMax
        xMax = other.xMax
    }
}