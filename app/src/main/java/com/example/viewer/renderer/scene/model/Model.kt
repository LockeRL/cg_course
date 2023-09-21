package com.example.viewer.renderer.scene.model

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseModel
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes
import java.util.Vector
import kotlin.math.cos
import kotlin.math.sin

class Model() : BaseModel() {
    override val vertices: Vector<Vector3D> = Vector<Vector3D>()
    override val triangles: Vector<Int> = Vector<Int>()
    override val normals: Vector<Vector3D> = Vector<Vector3D>()
    override val translation: Vector3D = Vector3D()
    override val material: Material = Material()
    override var rotation: Matrix = Matrix.identityMatrix(4)

    constructor(attributes: ModelAttributes, material: Material) : this() {
        this.material.set(material)
        computeVertices(attributes)
        computeTriangles(attributes.verticesNum)
        computeNormals(attributes.verticesNum)
    }

    override fun computeVertices(attributes: ModelAttributes) {
        val dAngle = 2.0 * Math.PI / attributes.verticesNum
        var angle = 0.0
        val halfHeight = attributes.height / 2
        for (i in 0 until attributes.verticesNum) {
            vertices.addElement(
                Vector3D(
                    attributes.lengthBot * cos(angle),
                    -halfHeight,
                    attributes.lengthBot * sin(angle)
                )
            )
            angle += dAngle
        }

        angle = 0.0
        for (i in 0 until attributes.verticesNum) {
            vertices.addElement(
                Vector3D(
                    attributes.lengthTop * cos(angle),
                    halfHeight,
                    attributes.lengthTop * sin(angle)
                )
            )
            angle -= dAngle
        }
    }

    override fun computeTriangles(verts: Int) {
        for (i in 0 until verts - 2) {
            triangles.addElement(0)
            triangles.addElement(i + 1)
            triangles.addElement(i + 2)
            triangles.addElement(verts)
            triangles.addElement(verts + i + 1)
            triangles.addElement(verts + i + 2)
        }
        for (i in 0 until verts) {
            val first = verts + i
            var second = verts - i
            val third = verts - i - 1
            var fourth = verts + i + 1
            second = if (second == verts) 0 else second
            fourth = if (fourth == verts * 2) verts else fourth
            triangles.addElement(first)
            triangles.addElement(second)
            triangles.addElement(third)
            triangles.addElement(first)
            triangles.addElement(third)
            triangles.addElement(fourth)
        }
    }

    override fun computeNormals(verts: Int) {
        for (i in 0 until verts - 2) {
            var normal = (vertices[i + 1] - vertices[0]).oper(vertices[i + 2] - vertices[i + 1])
            normal.normalize()
            normals.addElement(normal)
            normal =
                (vertices[verts + i + 1] - vertices[verts]).oper(vertices[verts + i + 2] - vertices[verts + i + 1])
            normal.normalize()
            normals.addElement(normal)
        }

        for (i in 0 until verts) {
            val first = verts + i
            var second = verts - i
            val third = verts - i - 1
            var fourth = verts + i + 1
            second = if (second == verts) 0 else second
            fourth = if (fourth == verts * 2) verts else fourth
            var normal =
                (vertices[second] - vertices[first]).oper(vertices[third] - vertices[second])
            normal.normalize()
            normals.addElement(normal)
            normal = (vertices[third] - vertices[first]).oper(vertices[fourth] - vertices[third])
            normal.normalize()
            normals.addElement(normal)
        }
    }

    override fun getAttributes(): ModelAttributes {
        val vertNum = countVertices() / 2
        return ModelAttributes(
            lengthBot = vertices[0].x,
            lengthTop = vertices[vertNum].x,
            height = -vertices[vertNum].y * 2.0,
            verticesNum = vertNum
        )
    }

    override fun changeVerticesCount(verts: Int) {
        val attributes = ModelAttributes(
            lengthBot = vertices[0].x,
            lengthTop = vertices[countVertices() / 2].x,
            height = -vertices[0].y * 2.0,
            verticesNum = verts
        )
        vertices.clear()
        triangles.clear()
        normals.clear()
        computeVertices(attributes)
        computeTriangles(attributes.verticesNum)
        computeNormals(attributes.verticesNum)
    }

    override fun changeTopLength(length: Double) {
        val vertNum = countVertices()
        val dAngle = 4.0 * Math.PI / vertNum
        var angle = 0.0
        for (i in vertNum / 2 until vertNum) {
            vertices[i].x = length * cos(angle)
            vertices[i].z = length * sin(angle)
            angle -= dAngle
        }
        normals.clear()
        computeNormals(vertNum / 2)
    }

    override fun changeBotLength(length: Double) {
        val vertNum = countVertices() / 2
        val dAngle = 2.0 * Math.PI / vertNum
        var angle = 0.0
        for (i in 0 until vertNum) {
            vertices[i].x = length * cos(angle)
            vertices[i].z = length * sin(angle)
            angle -= dAngle
        }
        normals.clear()
        computeNormals(vertNum)
    }

    override fun changeHeight(height: Double) {
        val vertNum = countVertices() / 2
        val halfHeight = height / 2
        for (i in 0 until vertNum) {
            vertices[i].y = -halfHeight
            vertices[i + vertNum].y = halfHeight
        }
        normals.clear()
        computeNormals(vertNum)
    }
}