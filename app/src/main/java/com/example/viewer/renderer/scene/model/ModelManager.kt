package com.example.viewer.renderer.scene.model

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseModel
import java.util.Vector

class ModelManager() {
    private val models = Vector<Model>()

    fun rotateByX(model: BaseModel, angle: Double) {
        model.rotation = model.rotation * Matrix.rotateXMatrix(angle)
    }

    fun rotateByY(model: BaseModel, angle: Double) {
        model.rotation = model.rotation * Matrix.rotateYMatrix(angle)
    }

    fun rotateByZ(model: BaseModel, angle: Double) {
        model.rotation = model.rotation * Matrix.rotateZMatrix(angle)
    }

    fun translateByX(model: BaseModel, translation: Double) {
        model.translation.set(model.translation + Vector3D(x = translation))
    }

    fun translateByY(model: BaseModel, translation: Double) {
        model.translation.set(model.translation + Vector3D(y = translation))
    }

    fun translateByZ(model: BaseModel, translation: Double) {
        model.translation.set(model.translation + Vector3D(z = translation))
    }

    fun getModelView(model: BaseModel): Matrix {
        val translation = Matrix.identityMatrix(4)
        val translate = model.translation
        translation[3, 0] = translate.x
        translation[3, 1] = translate.y
        translation[3, 2] = translate.z
        return model.rotation * translation
    }

}