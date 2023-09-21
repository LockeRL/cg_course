package com.example.viewer.renderer.scene.render

import android.graphics.Bitmap
import com.example.viewer.renderer.Controller
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.camera.data.CameraAttributes
import com.example.viewer.renderer.scene.camera.data.ViewFrustrum
import com.example.viewer.renderer.scene.light.data.LightAttributes
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes

class RenderWidget() {
    private lateinit var controller: Controller

    constructor(width: Int, height: Int, bitmap: Bitmap) : this() {
        controller = Controller(
            bitmap,
            width,
            height,
            CameraAttributes(
                Vector3D(z = 500.0),
                -90.0,
                0.0,
                Vector3D(y = 1.0),
                ViewFrustrum(
                    90.0,
                    0.1,
                    1000.0,
                    width.toDouble() / height
                )
            ),
            LightAttributes(Vector3D(y = 200.0), MaterialColor(255, 255, 255))
        )
    }

    fun addModel(attributes: ModelAttributes, material: Material) = controller.addModel(attributes, material)

    fun test() {
        controller.test()
    }

    fun deleteAllModels() {
        controller.deleteAllModels()
    }
}