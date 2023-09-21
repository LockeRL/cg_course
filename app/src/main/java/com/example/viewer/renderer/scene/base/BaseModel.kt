package com.example.viewer.renderer.scene.base

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes
import java.util.Vector

abstract class BaseModel {
    abstract val vertices: Vector<Vector3D>
    abstract val triangles: Vector<Int>
    abstract val normals: Vector<Vector3D>
    abstract var rotation: Matrix
    abstract val translation: Vector3D
    abstract val material: Material

    fun countVertices() = vertices.size
    fun countTriangles(): Int = triangles.size / 3
    fun getNormal(i: Int) = normals[i]
    fun getTriangle(num: Int): Array<Vector3D> {
        val i = num * 3
        return arrayOf(vertices[triangles[i]], vertices[triangles[i + 1]], vertices[triangles[i + 2]])
    }
//    fun getTriangle(num: Int) = Vector<Vector3D>().apply {
//        val i = num * 3
//        this.addElement(vertices[triangles[i]])
//        this.addElement(vertices[triangles[i + 1]])
//        this.addElement(vertices[triangles[i + 2]])
//    }

    protected abstract fun computeVertices(attributes: ModelAttributes)
    protected abstract fun computeTriangles(verts: Int)
    protected abstract fun computeNormals(verts: Int)

    abstract fun getAttributes(): ModelAttributes

    abstract fun changeVerticesCount(verts: Int)
    abstract fun changeTopLength(length: Double)
    abstract fun changeBotLength(length: Double)
    abstract fun changeHeight(height: Double)
}