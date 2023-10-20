package com.example.viewer.renderer

import android.graphics.Bitmap
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.raytraycer.Render

object RenderController {
    private val render = Render

    suspend fun render(canvas: Bitmap, camera: Camera, scene: Scene) {
        render.renderScene(scene, camera, canvas)
    }
}