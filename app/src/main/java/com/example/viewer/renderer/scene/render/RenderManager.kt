package com.example.viewer.renderer.scene.render

import android.graphics.Canvas
import android.graphics.Paint
import com.example.viewer.renderer.fill
import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.math.Vector4D
import com.example.viewer.renderer.math.toVector3D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.base.BaseModel
import com.example.viewer.renderer.scene.base.BaseShader
import kotlin.math.max
import kotlin.math.min

private const val SHADOW_MODEL_SIZE = 1024

class RenderManager() {
    private var screenWidth = 0
    private var screenHeight = 0
    private lateinit var objects: Array<Array<Int>>
    private lateinit var depthBuffer: Array<Array<Double>>
    private lateinit var shadowModel: ShadowModel
    private lateinit var frameBuffer: Canvas

    constructor(canvas: Canvas, width: Int, height: Int) : this() {
        screenWidth = width
        screenHeight = height
        frameBuffer = canvas
        shadowModel = ShadowModel(SHADOW_MODEL_SIZE, SHADOW_MODEL_SIZE)
        depthBuffer = Array(screenHeight) { Array(screenWidth) { 2.0 } }
        objects = Array(screenHeight) { Array(screenWidth) { -1 } }
        frameBuffer.fill(MaterialColor.BLACK)
    }

    fun getIndexByPosition(x: Int, y: Int) = objects[y][x]

    fun clearShadow() {
        shadowModel.clear()
    }

    fun test() {
        val p = Paint().apply { color = MaterialColor(26, 143, 17).toArgb() }
        val center = Vector3D(0.0, 0.0)
        frameBuffer.drawLine(
            20.0f,
            20.0f,
            600.0f,
            600.0f,
            p
        )
//        frameBuffer.drawRect(20.0f, 20.0f, 600.0f, 600.0f, p)
    }

    fun renderModel(shader: BaseShader, model: BaseModel, index: Int) {
        val result = Array(9) { Vector4D() }
        for (j in 0 until model.countTriangles()) {
            val triangle = model.getTriangle(j)
            val count = shader.vertex(result, triangle, model.getNormal(j))
            for (i in 0 until count - 2) {
                shader.geometry(arrayOf(result[i + 2], result[i + 1], result[0]))
                triangle[2] = result[0].toVector3D()
                triangle[1] = result[i + 1].toVector3D()
                triangle[0] = result[i + 2].toVector3D()
                renderTriangle(triangle, index, shader)
            }
        }
    }

    fun renderShadowModel(shader: SceneShader, model: BaseModel, bufferIndex: Int) {
        val result = Array(9) { Vector4D() }
        for (j in 0 until model.countTriangles()) {
            val triangle = model.getTriangle(j)
            val count = shader.vertex(result, triangle, model.getNormal(j))
            for (i in 0 until count - 2) {
                shader.geometry(arrayOf(result[i + 2], result[i + 1], result[0]))
                triangle[2] = result[0].toVector3D()
                triangle[1] = result[i + 1].toVector3D()
                triangle[0] = result[i + 2].toVector3D()
                renderShadowTriangle(triangle, bufferIndex, shader)
            }
        }
    }

    fun renderCoordinateSystem(rotation: Matrix) {
        val system = arrayOf(Vector3D(x = 1.0), Vector3D(y = 1.0), Vector3D(z = 1.0))
        system.forEachIndexed { i, vector ->
            system[i] = vector * rotation
        }
        val colors = arrayOf(
            MaterialColor(26, 143, 17).toArgb(),
            MaterialColor(189, 19, 7).toArgb(),
            MaterialColor(11, 57, 143).toArgb()
        )
        val letters = arrayOf("X", "Y", "Z")
        if (system[0].z > system[1].z) {
            system[0] = system[1].also { system[1] = system[0] }
            colors[0] = colors[1].also { colors[1] = colors[0] }
            letters[0] = letters[1].also { letters[1] = letters[0] }
        }
        if (system[1].z > system[2].z) {
            system[1] = system[2].also { system[2] = system[1] }
            colors[1] = colors[2].also { colors[2] = colors[1] }
            letters[1] = letters[2].also { letters[2] = letters[1] }
        }
        if (system[0].z > system[1].z) {
            system[0] = system[1].also { system[1] = system[0] }
            colors[0] = colors[1].also { colors[1] = colors[0] }
            letters[0] = letters[1].also { letters[1] = letters[0] }
        }

        val center = Vector3D(110.0, screenHeight - 50.0)
        for (i in 2 downTo 0) {
            val paint = Paint().apply { color = colors[i] }
            frameBuffer.drawLine(
                center.x.toFloat(),
                center.y.toFloat(),
                (center.x + system[i].x * 80).toFloat(),
                (center.y - system[i].y * 80).toFloat(),
                paint
            )
            frameBuffer.drawText(
                letters[i],
                (center.x + system[i].x * 100).toFloat(),
                (center.y - system[i].y * 100).toFloat(),
                paint
            )
        }

    }

    fun clearFrame() {
        frameBuffer.fill(MaterialColor.BLACK)
        depthBuffer.forEach { array ->
            array.forEachIndexed { i, _ ->
                array[i] = 2.0
            }
        }
        objects.forEach { array ->
            array.forEachIndexed { i, _ ->
                array[i] = -1
            }
        }
    }

    private fun renderTriangle(triangle: Array<Vector3D>, objectIndex: Int, shader: BaseShader) {
        triangle.forEachIndexed { i, _ ->
            viewPort(triangle[i], shadowModel.bufferWidth, shadowModel.bufferHeight)
            triangle[i].z = 1 / triangle[i].z
        }
        var leftCornerX = Int.MAX_VALUE
        var leftCornerY = Int.MAX_VALUE
        var rightCornerX = 0
        var rightCornerY = 0
        triangle.forEach { vector ->
            rightCornerX = max(rightCornerX, vector.x.toInt())
            rightCornerY = max(rightCornerY, vector.y.toInt())
            leftCornerX = min(leftCornerX, vector.x.toInt())
            leftCornerY = min(leftCornerY, vector.y.toInt())
        }
        rightCornerX = min(rightCornerX, shadowModel.bufferWidth - 1)
        rightCornerY = min(rightCornerY, shadowModel.bufferHeight - 1)
        val square =
            (triangle[0].y - triangle[2].y) * (triangle[1].x - triangle[2].x) + (triangle[1].y - triangle[2].y) * (triangle[2].x - triangle[0].x)

        renderFrameBuffer(triangle, objectIndex, shader, square, leftCornerX, rightCornerX, leftCornerY, rightCornerY) // разбить на потоки
    }

    private fun renderShadowTriangle(
        triangle: Array<Vector3D>,
        objectIndex: Int,
        shader: SceneShader
    ) {
        triangle.forEachIndexed { i, _ ->
            viewPort(triangle[i], shadowModel.bufferWidth, shadowModel.bufferHeight)
            triangle[i].z = 1 / triangle[i].z
        }
        var leftCornerX = Int.MAX_VALUE
        var leftCornerY = Int.MAX_VALUE
        var rightCornerX = 0
        var rightCornerY = 0
        triangle.forEach { vector ->
            rightCornerX = max(rightCornerX, vector.x.toInt())
            rightCornerY = max(rightCornerY, vector.y.toInt())
            leftCornerX = min(leftCornerX, vector.x.toInt())
            leftCornerY = min(leftCornerY, vector.y.toInt())
        }
        rightCornerX = min(rightCornerX, shadowModel.bufferWidth - 1)
        rightCornerY = min(rightCornerY, shadowModel.bufferHeight - 1)
        val square =
            (triangle[0].y - triangle[2].y) * (triangle[1].x - triangle[2].x) + (triangle[1].y - triangle[2].y) * (triangle[2].x - triangle[0].x)

        for (i in leftCornerX..rightCornerX)
            for (j in leftCornerY..rightCornerY) {
                val barCoords = barycentric(triangle, Vector3D(i.toDouble(), j.toDouble()), square)
                if ((barCoords.x >= 0.0) and (barCoords.y >= 0.0) and (barCoords.z >= 0.0)) {
                    val z = shader.countShadowDepth(barCoords)
                    if (z <= shadowModel.getDepthByIndex(objectIndex, i, j))
                        shadowModel.setPixel(objectIndex, i, j, z)
                }
            }
    }

    private fun renderFrameBuffer(
        triangle: Array<Vector3D>,
        objectIndex: Int,
        shader: BaseShader,
        square: Double,
        leftCornerX: Int,
        rightCornerX: Int,
        leftCornerY: Int,
        rightCornerY: Int
    ) {
        val paint = Paint()
        for (i in leftCornerX..rightCornerX)
            for (j in leftCornerY..rightCornerY) {
                val barCoords = barycentric(triangle, Vector3D(i.toDouble(), j.toDouble()), square)
                if ((barCoords.x >= 0.0) and (barCoords.y >= 0.0) and (barCoords.z >= 0.0)) {
                    val z = 1 / (barCoords.x * triangle[0].z + barCoords.y * triangle[1].z + barCoords.z * triangle[2].z)
                    if (z <= depthBuffer[j][i]) {
                        depthBuffer[j][i] = z
                        objects[j][i] = objectIndex
                        val col = shader.fragment(barCoords, shadowModel)
                        frameBuffer.drawPoint(i.toFloat(), j.toFloat(), paint.apply { color = col.toArgb() })
                    }
                }
            }
    }

    private fun barycentric(
        triangle: Array<Vector3D>,
        point: Vector3D,
        square: Double
    ): Vector3D {
        val barCoords = Vector3D()
        barCoords.x =
            ((point.y - triangle[2].y) * (triangle[1].x - triangle[2].x) + (triangle[1].y - triangle[2].y) * (triangle[2].x - point.x)) / square
        barCoords.y =
            ((point.y - triangle[0].y) * (triangle[2].x - triangle[0].x) + (triangle[2].y - triangle[0].y) * (triangle[0].x - point.x)) / square
        barCoords.z = 1 - barCoords.x - barCoords.y
        return barCoords
    }

    private fun viewPort(point: Vector3D, width: Int, height: Int) {
        point.x = (point.x + 1.0) * 0.5 * width
        point.y = height - (point.y + 1.0) * 0.5 * height
        point.z = (point.z + 2.0) * 0.5
    }

}