package com.example.viewer.renderer.math

import kotlin.math.sqrt

class Vector3D(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    operator fun plus(other: Vector3D) =
        Vector3D(x + other.x, y + other.y, z + other.z)

    operator fun plusAssign(other: Vector3D) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun minus(other: Vector3D) =
        Vector3D(x - other.x, y - other.y, z - other.z)

    operator fun minusAssign(other: Vector3D) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    operator fun times(other: Vector3D) = x * other.x + y * other.y + z * other.z

    operator fun times(double: Double) = Vector3D(x * double, y * double, z * double)

    operator fun timesAssign(double: Double) {
        x *= double
        y *= double
        z *= double
    }

    operator fun times(other: Matrix) = Vector3D(
        x * other[0, 0] + y * other[1, 0] + z * other[2, 0],
        x * other[0, 1] + y * other[1, 1] + z * other[2, 1],
        x * other[0, 2] + y * other[1, 2] + z * other[2, 2]
    )

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        else -> {
            throw IllegalArgumentException("wrong vector index")
        }
    }

    // operator ^
    fun oper(other: Vector3D) = Vector3D(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        z * other.y - y * other.x
    )

    fun oper(other: Matrix) = Vector3D(
        x * other[0, 0] + y * other[1, 0] + z * other[2, 0] + other[3, 0],
        x * other[0, 1] + y * other[1, 1] + z * other[2, 1] + other[3, 1],
        x * other[0, 2] + y * other[1, 2] + z * other[2, 2] + other[3, 2]
    )

    fun module() = sqrt(x * x + y * y + z * z)

    fun normalize() {
        val mod = module()
        x /= mod
        y /= mod
        z /= mod
    }

    fun set(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }
}