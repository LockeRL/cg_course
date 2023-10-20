package com.example.viewer.renderer.scene.objects.figure.complex

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive

class Cone(position: Vector3D, color: MaterialColor, material: Material) :
    BaseComplexFigure(position, color, material) {

    override fun createFigure(): List<BasePrimitive> {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

}