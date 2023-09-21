package com.example.viewer.renderer.scene.render

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.math.Vector4D
import com.example.viewer.renderer.math.toVector3D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.base.BaseShader
import com.example.viewer.renderer.scene.model.data.Material
import java.lang.Double.max
import kotlin.math.pow

class SceneShader() : BaseShader() {
    private val material = Material()
    private val w = Array(3) { 0.0 }
    private val triangle = Array(3) { Vector3D() }
    private val lightPosition = Vector3D()

    constructor(
        vpMat: Matrix,
        modelMat: Matrix,
        cameraPos: Vector3D,
        lightPos: Vector3D
    ) : this() {
        mvpMatrix = modelMat * vpMat
        modelMatrix = modelMat
        vpMatrix = vpMat
        invVpMatrix = vpMat.invertedMatrix()
        cameraPosition.set(cameraPos)
        lightPosition.set(lightPos)
    }


    override fun geometry(trig: Array<Vector4D>) {
        for (i in 0 until 3) {
            triangle[i] = (trig[i] * invVpMatrix).toVector3D()
            w[i] = 1.0 / trig[i].w
        }
    }

    override fun fragment(barycentric: Vector3D, shadowModel: ShadowModel): MaterialColor {
        val pos = depth(barycentric)
        val lightDir = pos - lightPosition
        val ambient = 0.1
        if (lightDir.module() - 5.0 < shadowModel.getDepthByVector(lightDir)) {
            lightDir.set(lightDir * -1.0)
            val eyeDir = (cameraPosition - pos).apply { normalize() }
            lightDir.normalize()
            val halfWay = (lightDir + eyeDir).apply { normalize() }
            val specComponent = max(normal * halfWay, 0.0).pow(material.shininess)
            val diffComponent = max(normal * lightDir, 0.0)
            return lightColor * (material.specular * specComponent + material.diffuse * diffComponent) + material.diffuse * ambient
        }
        return material.diffuse * ambient
    }

    fun setLightPosition(pos: Vector3D) {
        lightPosition.set(pos)
    }

    fun setMaterial(material: Material) {
        material.set(material)
    }

    fun countShadowDepth(barycentric: Vector3D): Double {
        return (lightPosition - depth(barycentric)).module()
    }

    private fun depth(barycentric: Vector3D): Vector3D {
        barycentric.x = barycentric.x * w[0]
        barycentric.y = barycentric.y * w[1]
        barycentric.z = barycentric.z * w[2]
        val w = 1 / (barycentric.x + barycentric.y + barycentric.z)
        return (triangle[0] * barycentric.x + triangle[1] * barycentric.y + triangle[2] * barycentric.z) * w
    }

}