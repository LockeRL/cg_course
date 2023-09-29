package com.example.viewer.renderer.scene

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseFigure
import com.example.viewer.renderer.scene.kdtree.FindIntersectionNodeAnswer
import com.example.viewer.renderer.scene.kdtree.FindPlaneAnswer
import com.example.viewer.renderer.scene.kdtree.KDNode
import com.example.viewer.renderer.scene.kdtree.KDTree
import com.example.viewer.renderer.scene.kdtree.Plane
import com.example.viewer.renderer.scene.kdtree.Voxel
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.toInt
import kotlin.coroutines.coroutineContext
import kotlin.math.min
import kotlin.math.sqrt

object Render {
    private val tracer = Tracer

    fun renderScene(scene: Scene, camera: Camera, canvas: Bitmap) {
        val width = canvas.width
        val height = canvas.height
        val dx = width / 2
        val dy = height / 2
        val focus = camera.projPlaneDist

        for (i in 0 until width)
            for (j in 0 until height) {
                val x = i - dx
                val y = j - dy

                val ray = Vector3D(x.toDouble(), y.toDouble(), focus)
                val color = tracer.trace(scene, camera, ray)
//                println("pix($i, $j), color: ${color.red}, ${color.blue}, ${color.green}")
                canvas.setPixel(i, j, color.toArgb())
            }

        // antialising
    }

    fun buildTree(scene: Scene) {
        tracer.buildTree(scene)
    }

    private const val ANTIALIASING = true

}