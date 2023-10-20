package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.scene.objects.figure.primitive.Triangle

class Plane(
    position: Vector3D,
    var length: Double,
    var width: Double,
    color: MaterialColor,
    material: Material
) : BaseComplexFigure(position, color, material) {

    override fun createFigure(): List<BasePrimitive> {
        val xf = position.x + length / 2
        val xb = position.x - length / 2

        val yl = position.y - width / 2
        val yr = position.y + width / 2

        val p1 = Vector3D(xf, yl, position.z)
        val p2 = Vector3D(xb, yl, position.z)
        val p3 = Vector3D(xb, yr, position.z)
        val p4 = Vector3D(xf, yr, position.z)

        return trianglesFromRect(p1, p2, p3, p4, color, material)
    }

    override fun update() {
        TODO("Not yet implemented")
    }


}