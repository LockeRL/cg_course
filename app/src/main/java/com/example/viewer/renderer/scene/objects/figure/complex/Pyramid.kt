package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive

class Pyramid(
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
        val topPoint = Vector3D(position.x, position.y, position.z + height / 2)
        val points = circlePoints(botCenter, rad, nVerts)

        return trianglesFromPoints(points, botCenter, color, material) +
                trianglesFromPoints(points, topPoint, color, material)
    }

}