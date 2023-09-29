package com.example.viewer.renderer.math

import kotlin.math.cos
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

    fun moduleSquare() = x * x + y * y + z * z

    fun set(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }

    operator fun component1() = x

    operator fun component2() = y

    operator fun component3() = z

    companion object {
        fun vecFromPoints(start: Vector3D, end: Vector3D) = end - start

        fun crossProduct(a: Vector3D, b: Vector3D) =
            Vector3D(a.z * b.y - a.y * b.z, a.x * b.z - a.z * b.x, a.y * b.x - a.x * b.y)

        fun rotateVectorX(p: Vector3D, sinAl: Double, cosAl: Double) =
            Vector3D(p.x, p.y * cosAl - p.z * sinAl, p.y * sinAl + p.z * cosAl)

        fun rotateVectorY(p: Vector3D, sinAl: Double, cosAl: Double) =
            Vector3D(p.x * cosAl - p.z * sinAl, p.y, p.x * sinAl + p.z * cosAl)

        fun rotateVectorZ(p: Vector3D, sinAl: Double, cosAl: Double) =
            Vector3D(p.x * cosAl - p.y * sinAl, p.x * sinAl + p.y * cosAl, p.z)

        fun cosVectors(v1: Vector3D, v2: Vector3D) = (v1 * v2) / sqrt(v1.moduleSquare() * v2.moduleSquare())
    }

}