package com.example.viewer.renderer.scene.base

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.math.Vector4D
import com.example.viewer.renderer.math.toVector4D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.render.ShadowModel
import java.util.Vector

abstract class BaseShader {
    protected var mvpMatrix = Matrix(4, 4)
    protected var invVpMatrix = Matrix(4, 4)
    protected var modelMatrix = Matrix(4, 4)
    protected var vpMatrix = Matrix(4, 4)
    protected val cameraPosition = Vector3D()
    protected val normal = Vector3D()
    private val planes: Array<Vector4D> =
        arrayOf(
            Vector4D(-1.0, 0.0, 0.0, 1.0),
            Vector4D(1.0, 0.0, 0.0, 1.0),
            Vector4D(0.0, -1.0, 0.0, 1.0),
            Vector4D(0.0, 1.0, 0.0, 1.0),
            Vector4D(0.0, 0.0, 1.0, 1.0),
            Vector4D(0.0, 0.0, -1.0, 1.0)
        )
    protected val lightColor = MaterialColor()

    abstract fun geometry(triangle: Array<Vector4D>)
    abstract fun fragment(barycentric: Vector3D, shadowModel: ShadowModel): MaterialColor

    fun updateVpMatrix(mat: Matrix) {
        vpMatrix = mat
        invVpMatrix = mat.invertedMatrix()
    }

    fun updateModelMatrix(mat: Matrix) {
        modelMatrix = mat
        mvpMatrix = mat * vpMatrix
    }

    fun updateCameraPosition(pos: Vector3D) {
        cameraPosition.set(pos)
    }

    fun updateLightColor(color: MaterialColor) {
        lightColor.set(color)
    }

    fun vertex(
        result: Array<Vector4D>,
        triangle: Array<Vector3D>,
        norm: Vector3D
    ): Int {
        var count = 0
        normal.set(norm * modelMatrix)

        if (normal * (triangle[0].oper(modelMatrix) - cameraPosition) < 0.0) {
            var clippedPolygon = Array(9) { Vector4D() }
            for (i in 0 until 3)
                clippedPolygon[i] = triangle[i].toVector4D(1.0) * mvpMatrix
            count = 3
            for (i in planes.indices) {
                count = clipPolygon(result, clippedPolygon, planes[i], count)
                if (count == 0) break
                clippedPolygon = result.clone()
            }
        }

        return count
    }

    private fun clipPolygon(
        result: Array<Vector4D>,
        polygon: Array<Vector4D>,
        plane: Vector4D,
        length: Int
    ): Int {
        var first = length - 1
        var count = 0
        var second = 0
        for (i in 0 until length) {
            second = i
            if (pointIsVisible(plane, polygon[second])) {
                if (pointIsVisible(plane, polygon[first])) {
                    result[count++] = polygon[second]
                } else {
                    result[count++] = findIntersection(plane, polygon[first], polygon[second])
                    result[count++] = polygon[second]
                }
            } else {
                if (pointIsVisible(plane, polygon[first])) {
                    result[count++] = findIntersection(plane, polygon[first], polygon[second])
                }
            }
            first = second
        }
        return count
    }

    private fun findIntersection(plane: Vector4D, a: Vector4D, b: Vector4D): Vector4D {
        val da = plane * a
        val db = plane * b
        val t = da / (da - db)
        return a + (b - a) * t
    }

    private fun pointIsVisible(plane: Vector4D, point: Vector4D) = plane * point > 0.0
}