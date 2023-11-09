package com.example.viewer.renderer.raytraycer

import com.example.viewer.renderer.EPS
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.raytraycer.kdtree.KDTreeController
import com.example.viewer.renderer.scene.objects.Camera
import kotlin.math.abs
import kotlin.math.pow

object Tracer {
    private val treeController = KDTreeController

    fun trace(scene: Scene, camera: Camera, vec: Vector3D) =
        traceRecursively(scene, camera.position, camera.rotateVec(vec), INITIAL_RAY_INTENSITY, 0)

    fun buildTree(scene: Scene) {
        println("in tracer")
        val objects = scene.primitives.toMutableList()
        scene.tree = treeController.buildKDTree(objects)
    }

    private fun traceRecursively(
        scene: Scene,
        vecStart: Vector3D,
        vec: Vector3D,
        intensity: Double,
        recLevel: Int
    ): MaterialColor {
        vec.normalize()
        val answer = treeController.findIntersectionTree(scene.tree, vecStart, vec)
        if (answer.status)
            return calculateColor(
                scene,
                vec,
                answer.nearestObject!!,
                answer.nearestPoint!!,
                answer.nearestPointDist,
                intensity,
                recLevel
            )
        return scene.bgColor
    }

    private fun calculateColor(
        scene: Scene,
        vec: Vector3D,
        obj: BasePrimitive,
        point: Vector3D,
        dist: Double,
        intensity: Double,
        recLevel: Int
    ): MaterialColor {
        val material = obj.material
        val norm = obj.getNormalVector(point)

        val objColor = obj.color
        val ambientColor = MaterialColor()
        val diffuseColor = MaterialColor()
        var reflectedColor = MaterialColor()
        val specularColor = MaterialColor()

        var reflectedRay = Vector3D()
        if ((material.ks > 0.0) || (material.kr > 0.0))
            reflectedRay = reflectRay(vec, norm)

        if (material.ka > 0.0)
            ambientColor += scene.bgColor * objColor

        if (material.kd > 0.0)
            diffuseColor += if (scene.lights.isNotEmpty()) objColor * getLightingColor(
                point,
                norm,
                scene
            ) else objColor

        if (material.ks > 0.0)
            specularColor += if (scene.lights.isNotEmpty()) getSpecularColor(
                point,
                reflectedRay,
                scene,
                material.p
            ) else scene.bgColor

        if (material.kr > 0.0) {
            if ((intensity > THRESHOLD_RAY_INTENSITY) && (recLevel < MAX_RAY_RECURSION_LEVEL))
                reflectedColor = traceRecursively(
                    scene,
                    point,
                    reflectedRay,
                    intensity * material.kr,
                    recLevel + 1
                )
        } else
            reflectedColor = scene.bgColor

        val resColor = MaterialColor()
        if (material.ka > 0.0)
            resColor += ambientColor * material.ka

        if (material.kd > 0.0)
            resColor += diffuseColor * material.kd

        if (material.ks > 0.0)
            resColor += specularColor * material.ks

        if (material.kr > 0.0)
            resColor += reflectedColor * material.kr

        return resColor
    }

    private fun getLightingColor(point: Vector3D, norm: Vector3D, scene: Scene): MaterialColor {
        val col = MaterialColor()
//        norm.normalize()
        scene.lights.forEach { light ->
            if (isViewable(light.position, point, scene)) {
                val vLs = Vector3D.vecFromPoints(point, light.position)
//                vLs.normalize()
                val cosLs = abs(Vector3D.cosVectors(norm, vLs))
                val colorLs = light.color * cosLs
                col += colorLs
            }
        }

        return col
    }

    private fun getSpecularColor(
        point: Vector3D,
        reflectRay: Vector3D,
        scene: Scene,
        p: Double
    ): MaterialColor {
        val col = MaterialColor()
//        reflectRay.normalize()
        scene.lights.forEach { light ->
            if (isViewable(light.position, point, scene)) {
                val vLs = Vector3D.vecFromPoints(point, light.position)
//                vLs.normalize()
                val cosLs = Vector3D.cosVectors(reflectRay, vLs)
                if (cosLs > EPS)
                    col += light.color * cosLs.pow(p)
            }
        }

        return col
    }

    private fun isViewable(target: Vector3D, start: Vector3D, scene: Scene): Boolean {
        val ray = Vector3D.vecFromPoints(start, target)
        val dist = ray.module()
        ray.normalize()

        val ans = treeController.findIntersectionTree(scene.tree!!, start, ray)
        if (ans.status)
            return dist < ans.nearestPointDist

        return true
    }

    private fun reflectRay(incidentRay: Vector3D, norm: Vector3D): Vector3D {
        val k = 2 * (incidentRay * norm) / norm.moduleSquare()
        return incidentRay - norm * k
    }

    private const val INITIAL_RAY_INTENSITY = 100.0
    private const val THRESHOLD_RAY_INTENSITY = 10.0
    private const val MAX_RAY_RECURSION_LEVEL = 5

}
