package com.example.viewer.renderer.scene.objects.figure.base

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.primitive.Triangle
import kotlin.math.cos
import kotlin.math.sin

abstract class BaseComplexFigure(
    position: Vector3D,
    var color: MaterialColor,
    var material: Material
) : BaseObject(position) {

    val parts by lazy { createFigure() }

    override fun rotate(ang: Vector3D) {
        parts.forEach { it.rotate(ang, position) }
    }

    override fun move(vec: Vector3D) {
        position = position + vec
        parts.forEach { it.move(vec) }
    }

    protected abstract fun createFigure(): List<BasePrimitive>

    protected fun trianglesFromRect(
        leftBot: Vector3D,
        leftTop: Vector3D,
        rightTop: Vector3D,
        rightBot: Vector3D,
        color: MaterialColor,
        material: Material
    ) = listOf(
        Triangle(leftBot.copy(), rightBot.copy(), leftTop.copy(), color, material),
        Triangle(rightTop.copy(), rightBot.copy(), leftTop.copy(), color, material)
    )

    protected fun circlePoints(center: Vector3D, rad: Double, n: Int): List<Vector3D> {
        val da = 2 * Math.PI / n
        var ang = 0.0
        val points = mutableListOf<Vector3D>()
        for (i in 0 until n) {
            points += Vector3D(rad * cos(ang), rad * sin(ang), 0.0) + center
            ang += da
        }
        return points
    }

    protected fun trianglesFromPoints(
        points: List<Vector3D>,
        center: Vector3D,
        color: MaterialColor,
        material: Material
    ): List<Triangle> {
        val triangles = mutableListOf<Triangle>()
        val n = points.size
        for (i in 0 until n) {
            triangles += Triangle(
                points[i].copy(),
                points[(i + 1) % n].copy(),
                center.copy(),
                color,
                material
            )
        }
        return triangles
    }

}