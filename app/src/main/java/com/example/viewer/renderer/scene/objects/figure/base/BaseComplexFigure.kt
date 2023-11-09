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

    abstract fun update()

    protected abstract fun createFigure(): List<BasePrimitive>

    protected fun trianglesFromRect(
        leftBot: Vector3D,
        leftTop: Vector3D,
        rightTop: Vector3D,
        rightBot: Vector3D,
        color: MaterialColor,
        material: Material
    ) = listOf(
        Triangle(leftBot, rightBot, leftTop, color, material),
        Triangle(rightTop, rightBot, leftTop, color, material)
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
                points[i],
                points[(i + 1) % n],
                center,
                color,
                material
            )
        }
        return triangles
    }

}