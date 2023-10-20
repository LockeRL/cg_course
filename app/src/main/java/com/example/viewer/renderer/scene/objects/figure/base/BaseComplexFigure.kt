package com.example.viewer.renderer.scene.objects.figure.base

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.primitive.Triangle

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

}