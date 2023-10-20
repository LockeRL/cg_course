package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.EPS
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.scene.objects.figure.primitive.Triangle

class Cube(
    position: Vector3D,
    var length: Double,
    var width: Double,
    var height: Double,
    color: MaterialColor,
    material: Material
) : BaseComplexFigure(position, color, material) {

    override fun createFigure(): List<BasePrimitive> {
        val xf = position.x + length / 2 + EPS
        val xb = position.x - length / 2 - EPS

        val zb = position.z - height / 2 - EPS
        val zt = position.z + height / 2 + EPS

        val yl = position.y - width / 2 - EPS
        val yr = position.y + width / 2 + EPS

        val b1 = Vector3D(xf, yl, zb)
        val b2 = Vector3D(xb, yl, zb)
        val b3 = Vector3D(xb, yr, zb)
        val b4 = Vector3D(xf, yr, zb)

        val t1 = Vector3D(xf, yl, zt)
        val t2 = Vector3D(xb, yl, zt)
        val t3 = Vector3D(xb, yr, zt)
        val t4 = Vector3D(xf, yr, zt)

        return trianglesFromRect(b1, b2, b3, b4, color, material) +
                trianglesFromRect(t1, t2, t3, t4, color, material) +
                trianglesFromRect(b1, t1, t4, b4, color, material) +
                trianglesFromRect(b2, t2, t3, t4, color, material) +
                trianglesFromRect(b1, t1, t2, b2, color, material) +
                trianglesFromRect(b4, t4, t3, b3, color, material)
    }

    override fun update() {

    }

}