package com.example.viewer.renderer.scene.objects.figure

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.math.Vector3D.Companion.crossProduct
import com.example.viewer.renderer.math.Vector3D.Companion.vecFromPoints
import com.example.viewer.renderer.scene.Material
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.base.BaseFigure
import java.text.FieldPosition


class Triangle(
    var p1: Vector3D,
    var p2: Vector3D,
    var p3: Vector3D,
    color: MaterialColor,
    material: Material,
    var n1: Vector3D = Vector3D(),
    var n2: Vector3D = Vector3D(),
    var n3: Vector3D = Vector3D()
) : BaseFigure(position = Vector3D(), color, material) {
    val norm = crossProduct(vecFromPoints(p1, p3), vecFromPoints(p3, p2))
    val dist = -(p1.x * norm.x + p1.y * norm.y + p1.z * norm.z)

    val vP1P2 = vecFromPoints(p1, p2)
    val vP2P3 = vecFromPoints(p2, p3)
    val vP3P1 = vecFromPoints(p3, p1)

//    Texture canvas + points 2d

    override fun intersect(vecStart: Vector3D, vec: Vector3D): Vector3D? {
        val prod = norm * vec
        if (prod == 0.0)
            return null

        val k = -(norm * vecStart + dist) / prod
        if (k < 0.0)
            return null

        val ipt = vecStart + vec * k
        return if (
            checkSameClockDir(vP1P2, vecFromPoints(p1, ipt)) &&
            checkSameClockDir(vP2P3, vecFromPoints(p2, ipt)) &&
            checkSameClockDir(vP3P1, vecFromPoints(p3, ipt))
        ) ipt else null
    }

    override fun getNormalVector(point: Vector3D) = norm

    fun getPhongNormalVector(point: Vector3D): Vector3D {
        val (w1, w2, w3) = getWeightsOfVertexes(point)
        return Vector3D(
            w1 * n1.x + w2 * n2.x + w3 * n3.x,
            w1 * n1.y + w2 * n2.y + w3 * n3.y,
            w1 * n1.z + w2 * n2.z + w3 * n3.z
        )
    }

    override fun getMinBoundaryPoint() =
        Vector3D(minOf(p1.x, p2.x, p3.x), minOf(p1.y, p2.y, p3.y), minOf(p1.z, p2.z, p3.z))

    override fun getMaxBoundaryPoint() =
        Vector3D(maxOf(p1.x, p2.x, p3.x), maxOf(p1.y, p2.y, p3.y), maxOf(p1.z, p2.z, p3.z))

    private fun getWeightsOfVertexes(point: Vector3D): Vector3D {
        val s1 = crossProduct(vecFromPoints(p2, point), vP2P3).module()
        val s2 = crossProduct(vecFromPoints(p3, point), vP3P1).module()
        val s3 = crossProduct(vecFromPoints(p1, point), vP1P2).module()

        val sum = s1 + s2 + s3

        return Vector3D(s1 / sum, s2 / sum, s3 / sum)
    }

    private fun checkSameClockDir(v1: Vector3D, v2: Vector3D) =
        crossProduct(v2, v1) * norm > 0.0

}