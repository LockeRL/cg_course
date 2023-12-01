package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.scene.objects.figure.primitive.Triangle

class Prism(
    position: Vector3D,
    var height: Double,
    var rad: Double,
    var nVerts: Int,
    color: MaterialColor,
    material: Material
) :
    BaseComplexFigure(position, color, material) {

    override fun createFigure(): List<BasePrimitive> {
        val botCenter = Vector3D(position.x, position.y, position.z - height / 2)
        val topCenter = Vector3D(position.x, position.y, position.z + height / 2)
        val topPoints = circlePoints(topCenter, rad, nVerts)
        val botPoints = circlePoints(botCenter, rad, nVerts)

        val sideFaces = mutableListOf<Triangle>()
        for (i in 0 until nVerts) {
            val second = (i + 1) % nVerts
            sideFaces += trianglesFromRect(
                botPoints[i],
                topPoints[i],
                topPoints[second],
                botPoints[second],
                color,
                material
            )
        }
        return trianglesFromPoints(botPoints, botCenter, color, material) +
                trianglesFromPoints(topPoints, topCenter, color, material) +
                sideFaces
    }

}