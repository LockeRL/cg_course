package com.example.viewer.renderer.math

import kotlin.math.sqrt

class Vector4D(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0, var w: Double = 0.0) {
    operator fun plus(other: Vector4D) =
        Vector4D(x + other.x, y + other.y, z + other.z, w + other.w)

    operator fun plusAssign(other: Vector4D) {
        x += other.x
        y += other.y
        z += other.z
        w += other.w
    }

    operator fun minus(other: Vector4D) =
        Vector4D(x - other.x, y - other.y, z - other.z, w - other.w)

    operator fun minusAssign(other: Vector4D) {
        x -= other.x
        y -= other.y
        z -= other.z
        w -= other.w
    }

    operator fun times(other: Vector4D) = x * other.x + y * other.y + z * other.z + w * other.w

    operator fun times(double: Double) = Vector4D(x * double, y * double, z * double, w * double)

    operator fun timesAssign(double: Double) {
        x *= double
        y *= double
        z *= double
        w *= double
    }

    operator fun times(other: Matrix) = Vector4D(
            x * other[0, 0] + y * other[1, 0] + z * other[2, 0] + w * other[3, 0],
            x * other[0, 1] + y * other[1, 1] + z * other[2, 1] + w * other[3, 1],
            x * other[0, 2] + y * other[1, 2] + z * other[2, 2] + w * other[3, 2],
            x * other[0, 3] + y * other[1, 3] + z * other[2, 3] + w * other[3, 3]
        )

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        3 -> w
        else -> {
            throw IllegalArgumentException("wrong vector index")
        }
    }

    fun module() = sqrt(x * x + y * y + z * z + w * w)

    fun normalize() {
        val mod = module()
        x /= mod
        y /= mod
        z /= mod
        w /= mod
    }

    fun set(other: Vector4D) {
        x = other.x
        y = other.y
        z = other.z
        w = other.w
    }

}