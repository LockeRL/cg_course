package com.example.viewer.renderer.scene.light

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseUtilObject
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.light.data.LightAttributes
import com.example.viewer.renderer.scene.model.Model
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes

class Light() : BaseUtilObject {
    override val position = Vector3D()
    val color = MaterialColor()
    var model = Model()

    constructor(attributes: LightAttributes) : this() {
        position.set(attributes.position)
        setColor(attributes.color)
        model =
            Model(
                ModelAttributes(0.0, 10.0, 15.0, 3),
                Material(specular = color, shininess = 1)
            )
    }

    fun setPosition(pos: Vector3D) {
        position.set(pos)
        model.translation.set(pos)
    }

    fun setColor(col: MaterialColor) {
        color.set(col)
    }

}