package com.example.viewer.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.Scene
import com.example.viewer.renderer.scene.camera.Camera
import com.example.viewer.renderer.scene.camera.CameraManager
import com.example.viewer.renderer.scene.camera.data.CameraAttributes
import com.example.viewer.renderer.scene.camera.data.ViewFrustrum
import com.example.viewer.renderer.scene.light.Light
import com.example.viewer.renderer.scene.light.data.LightAttributes
import com.example.viewer.renderer.scene.model.Model
import com.example.viewer.renderer.scene.model.ModelManager
import com.example.viewer.renderer.scene.model.data.Material
import com.example.viewer.renderer.scene.model.data.ModelAttributes
import com.example.viewer.renderer.scene.render.LightShader
import com.example.viewer.renderer.scene.render.RenderManager
import com.example.viewer.renderer.scene.render.SceneShader

class Controller() {
    private lateinit var cameraManager: CameraManager
    private var modelManager = ModelManager()
    private lateinit var renderManager: RenderManager
    private val scene = Scene()

    private val shadowDir =
        arrayOf(
            Vector3D(1.0, 0.0, 0.0),
            Vector3D(-1.0, 0.0, 0.0),
            Vector3D(0.0, 1.0, 0.0),
            Vector3D(0.0, -1.0, 0.0),
            Vector3D(0.0, 0.0, 1.0),
            Vector3D(0.0, 0.0, -1.0)
        )
    private val shadowUp =
        arrayOf(
            Vector3D(1.0, 0.0, 0.0),
            Vector3D(-1.0, 0.0, 0.0),
            Vector3D(0.0, 1.0, 0.0),
            Vector3D(0.0, -1.0, 0.0),
            Vector3D(0.0, 0.0, 1.0),
            Vector3D(0.0, 0.0, -1.0)
        )
    private val shadowRight =
        arrayOf(
            Vector3D(0.0, 0.0, -1.0),
            Vector3D(0.0, 0.0, 1.0),
            Vector3D(1.0, 0.0, 0.0),
            Vector3D(1.0, 0.0, 0.0),
            Vector3D(1.0, 0.0, 0.0),
            Vector3D(-1.0, 0.0, 0.0)
        )

    constructor(
        bitmap: Bitmap,
        width: Int,
        height: Int,
        cameraAttributes: CameraAttributes,
        lightAttributes: LightAttributes
    ) : this() {
        renderManager = RenderManager(Canvas(bitmap), width, height)
        cameraManager = CameraManager(2.0, 0.25)
        scene.camera = Camera(cameraAttributes)
        scene.light = Light(lightAttributes)
        renderScene()
        println("in render constructor")
    }

    fun changeLight(attributes: LightAttributes) {
        scene.light.setPosition(attributes.position)
        scene.light.setColor(attributes.color)
        renderScene()
    }

    fun test() {
        renderManager.test()
    }

    fun addModel(modelAttributes: ModelAttributes, material: Material): Int {
        println("in controller add model")
        scene.addModel(Model(modelAttributes, material))
        renderShadow()
        renderScene()
        return scene.countModels()
    }

//    fun getModelAttributes(id: Int) = scene.getModel(id).getAttributes()

    fun getModelMaterial(id: Int) = scene.getModel(id).material

    fun deleteModel(id: Int) {
        scene.deleteModel(id)
        renderShadow()
        renderScene()
    }

    fun changeModel(id: Int) {
        val model = scene.getModel(id)
        TODO()
        renderScene()
        renderShadow()
    }

    fun changeCamera() {
        TODO()
    }

    fun deleteAllModels() {
        scene.deleteAllModels()
        renderShadow()
        renderScene()
    }

    private fun renderScene() {
        println("in render scene")
        val camera = scene.camera
        val light = scene.light
        renderManager.clearFrame()
        val rotation = cameraManager.getRotation(camera)
        val vpMatrix = cameraManager.getLookAt(camera) * cameraManager.getProjection(camera)
        val sceneShader = SceneShader()
        sceneShader.updateCameraPosition(camera.position)
        sceneShader.setLightPosition(light.position)
        sceneShader.updateVpMatrix(vpMatrix)
        for (i in 0 until scene.countModels()) {
            val model = scene.getModel(i)
            val modelMatrix = modelManager.getModelView(model)
            sceneShader.updateModelMatrix(modelMatrix)
            sceneShader.setMaterial(model.material)
            sceneShader.updateLightColor(light.color)
            renderManager.renderModel(sceneShader, model, i)
        }
        val lightShader = LightShader()
        lightShader.updateCameraPosition(camera.position)
        lightShader.updateVpMatrix(vpMatrix)
        val model = light.model
        val modelMatrix = modelManager.getModelView(model)
        lightShader.updateModelMatrix(modelMatrix)
        lightShader.updateLightColor(light.color)
        renderManager.renderModel(lightShader, model, -1)
        renderManager.renderCoordinateSystem(rotation)
    }

    private fun renderShadow() {
        println("in render shadow")
        val light = scene.light
        val camera = Camera()
        camera.setFrustrum(ViewFrustrum(90.0, 0.1, 1000.0, 1.0))
        renderManager.clearShadow()
        val projection = cameraManager.getProjection(camera)
        val shader = SceneShader()
        shader.updateCameraPosition(light.position)
        shader.setLightPosition(light.position)
        camera.setPosition(light.position)
        for (j in 0 until 6) {
            camera.setDirection(shadowDir[j])
            camera.setUp(shadowUp[j])
            camera.setRight(shadowRight[j])
            val vpMatrix = cameraManager.getLookAt(camera) * projection
            shader.updateVpMatrix(vpMatrix)
            for (i in 0 until scene.countModels()) {
                val model = scene.getModel(i)
                val modelMatrix = modelManager.getModelView(model)
                shader.updateModelMatrix(modelMatrix)
                renderManager.renderShadowModel(shader, model, j)
            }
        }
    }

}