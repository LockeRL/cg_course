package com.example.viewer.renderer.scene

import com.example.viewer.renderer.scene.camera.Camera
import com.example.viewer.renderer.scene.light.Light
import com.example.viewer.renderer.scene.model.Model
import java.util.Vector

class Scene() {
    var camera = Camera()
    var light = Light()
    private val models: Vector<Model> = Vector()

    fun countModels() = models.size

    fun getModel(id: Int): Model = models[id]

    fun setModel(model: Model, id: Int) {
        models[id] = model
    }

    fun addModel(model: Model) {
        models.addElement(model)
    }

    fun deleteModel(id: Int) {
        models.removeAt(id)
    }

    fun deleteAllModels() {
        models.clear()
    }

}