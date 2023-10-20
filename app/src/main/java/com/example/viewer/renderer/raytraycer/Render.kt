package com.example.viewer.renderer.raytraycer

import android.graphics.Bitmap
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.renderer.scene.objects.Camera

object Render {
    private val tracer = Tracer

    suspend fun renderScene(scene: Scene, camera: Camera, canvas: Bitmap) {
        val width = canvas.width
        val height = canvas.height
        val dx = width / 2
        val dy = height / 2
        val focus = camera.projPlaneDist

//        val newCamera = NewCamera(
//            Vector3D(0.0, 1000.0, 0.0),
//        Vector3D(),
//        Vector3D(y = 1.0)
//        )
//        val view = Vector3D.vecFromPoints(newCamera.position, newCamera.direction).apply { normalize() }
//        val cameraRight = Vector3D.crossProduct(view, newCamera.up).apply { normalize() }
//        val cameraUp = Vector3D.crossProduct(cameraRight, view).apply { normalize() }
//        val mtanf = tan(3.0)
//        val maspect = 4.0 / 3
        for (i in 0 until width)
            for (j in 0 until height) {
                    val x = i - dx
                    val y = j - dy

                    val ray = Vector3D(x.toDouble(), y.toDouble(), focus)
//                val ray = cameraRight * mtanf * maspect * x + cameraUp * y * mtanf + view
                    val color = Tracer.trace(scene, camera, ray)
//                        println("pix($i, $j), color: ${color.red}, ${color.blue}, ${color.green}")
                    canvas.setPixel(i, j, color.toArgb())
                }

        // antialising
    }

    fun buildTree(scene: Scene) {
        Tracer.buildTree(scene)
    }

    private const val ANTIALIASING = true

}