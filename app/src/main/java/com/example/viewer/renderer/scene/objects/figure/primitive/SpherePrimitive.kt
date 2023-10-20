package com.example.viewer.renderer.scene.objects.figure.primitive

import com.example.viewer.renderer.EPS
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class SpherePrimitive(var radius: Double, position: Vector3D, color: MaterialColor, material: Material) :
    BasePrimitive(position, color, material) {

    override fun intersect(vecStart: Vector3D, vec: Vector3D): Vector3D? {
        val a = vec.moduleSquare()
        val b = 2 * (vec * (vecStart - position))
        val c =
            position.moduleSquare() + vecStart.moduleSquare() - 2 * (vecStart * position) - radius * radius
        val d = b * b - 4 * a * c

        if (d < 0.0)
            return null

        val sqrD = sqrt(d)
        val doubleA = 2 * a
        val t1 = (-b + sqrD) / doubleA
        val t2 = (-b - sqrD) / doubleA

        val minT = min(t1, t2)
        val maxT = max(t1, t2)

        val t = if (minT > EPS) minT else maxT
        if (t < EPS)
            return null

        return vecStart + vec * t
    }

    override fun getMinBoundaryPoint() =
        Vector3D(position.x - radius - 1.0, position.y - radius - 1.0, position.z - radius - 1.0)

    override fun getMaxBoundaryPoint() =
        Vector3D(position.x + radius + 1.0, position.y + radius + 1.0, position.z + radius + 1.0)

    override fun getNormalVector(point: Vector3D) =
        Vector3D.vecFromPoints(position, point).apply { normalize() }

    override fun move(vec: Vector3D) {
        position += vec
    }

    override fun rotate(ang: Vector3D) { }

}
