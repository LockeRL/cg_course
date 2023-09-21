package com.example.viewer.renderer.scene.model.data

import com.example.viewer.renderer.scene.MaterialColor

data class Material(
    var diffuse: MaterialColor = MaterialColor(),
    var specular: MaterialColor = MaterialColor(),
    var shininess: Int = 0
) {
    fun set(other: Material) {
        diffuse.set(other.diffuse)
        specular.set(other.specular)
        shininess = other.shininess
    }
}
