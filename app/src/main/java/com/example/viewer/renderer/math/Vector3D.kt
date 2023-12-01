package com.example.viewer.renderer.math

import com.example.viewer.renderer.EPS
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
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

    operator fun get(i: Int) = when (i) {
        0 -> x
        1 -> y
        2 -> z
        else -> {
            throw IllegalArgumentException("wrong vector index")
        }
    }

    fun module() = sqrt(moduleSquare())

    fun normalize() {
        val mod = module()
        if (abs(mod) < EPS)
            return
        x /= mod
        y /= mod
        z /= mod
    }

    fun moduleSquare() = this * this

    fun set(other: Vector3D) {
        x = other.x
        y = other.y
        z = other.z
    }

    fun rotateOX(ang: Double, center: Vector3D) {
        val dy = y - center.y
        val dz = z - center.z
        y = center.y + dy * cos(ang) - dz * sin(ang)
        z = center.z + dy * sin(ang) + dz * cos(ang)
    }

    fun rotateOY(ang: Double, center: Vector3D) {
        val dx = x - center.x
        val dz = z - center.z
        x = center.x + dx * cos(ang) - dz * sin(ang)
        z = center.z + dx * sin(ang) + dz * cos(ang)
    }

    fun rotateOZ(ang: Double, center: Vector3D) {
        val dx = x - center.x
        val dy = y - center.y
        x = center.x + dx * cos(ang) - dy * sin(ang)
        y = center.y + dx * sin(ang) + dy * cos(ang)
    }

    fun copy() = Vector3D(x, y, z)

    fun print() {
        println("x: $x, y: $y, z: $z")
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