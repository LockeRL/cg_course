package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.scene.objects.figure.primitive.SpherePrimitive

class Sphere(position: Vector3D, var radius: Double, color: MaterialColor, material: Material) :
    BaseComplexFigure(position, color, material) {

    override fun createFigure(): List<BasePrimitive> = listOf(SpherePrimitive(radius, position, color, material))

    override fun update() {}

}