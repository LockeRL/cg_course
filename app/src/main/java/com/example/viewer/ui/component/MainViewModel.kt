package com.example.viewer.ui.component

import android.R
import android.app.Application
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.widget.ArrayAdapter
import android.widget.BaseExpandableListAdapter
import com.example.viewer.renderer.RenderController
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.SceneController
import com.example.viewer.renderer.scene.material.Material
import com.example.viewer.renderer.scene.material.MaterialColor
import com.example.viewer.renderer.scene.objects.Camera
import com.example.viewer.renderer.scene.objects.Light
import com.example.viewer.renderer.scene.objects.figure.base.BaseComplexFigure
import com.example.viewer.renderer.scene.objects.figure.base.BaseObject
import com.example.viewer.renderer.scene.objects.figure.complex.Cube
import com.example.viewer.renderer.scene.objects.figure.complex.Plane
import com.example.viewer.renderer.scene.objects.figure.complex.Prism
import com.example.viewer.renderer.scene.objects.figure.complex.Pyramid
import com.example.viewer.renderer.scene.objects.figure.complex.Sphere
import com.example.viewer.ui.base.BaseViewModel
import com.example.viewer.ui.util.ObjectType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.PI


class MainViewModel : BaseViewModel() {

    private val _canvas: MutableStateFlow<Bitmap?> = MutableStateFlow(null)
    val canvas: StateFlow<Bitmap?> = _canvas

    val objectsArray = ArrayList<String>()
    var activeItem = 0
    private var mainCamera = 0

    private val sceneController = SceneController
    private val renderController = RenderController

    fun initCanvas(width: Int, height: Int) {
        val canvasWidth = (width * WIDTH_PERCENT).toInt()
        val canvasHeight = (height * HEIGHT_PERCENT).toInt()
        _canvas.value = createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
    }

    fun act() {
        val sphere = Sphere(
            Vector3D(),
            300.0,
            MaterialColor(255, 0, 0),
            Material(1.0, 1.0, 1.0, 1.0, 10.0)
        )
        sceneController.addFigure(sphere)
//        sceneController.moveObject(0, Vector3D(100.0))

//        val cube = Cube(
//            Vector3D(),
//            400.0,
//            400.0,
//            400.0,
//            MaterialColor(250, 30, 30),
//            Material(4.0, 5.0, 5.0, 3.0, 10.0)
//        )
//        sceneController.addFigure(cube)
//        sceneController.rotateObject(0, Vector3D(z = PI / 4))
//        sceneController.moveObject(0, Vector3D(200.0))

//        val triangPyramid = Pyramid(
//            Vector3D(),
//            600.0,
//            400.0,
//            10,
//            MaterialColor(250, 30, 30),
//            Material(4.0, 5.0, 5.0, 3.0, 10.0)
//        )
//        sceneController.addFigure(triangPyramid)
//        sceneController.rotateObject(0, Vector3D(y = PI))
//        sceneController.moveObject(0, Vector3D(100.0))


//        val rectPyramid = Prism(
//            Vector3D(),
//            600.0,
//            400.0,
//            15,
//            MaterialColor(250, 30, 30),
//            Material(4.0, 5.0, 5.0, 3.0, 10.0)
//        )
//        sceneController.addFigure(rectPyramid)

//        val plane = Plane(
//            Vector3D(),
//            400.0,
//            400.0,
//            MaterialColor(250, 30, 30),
//            Material(1.0, 5.0, 5.0, 10.0, 10.0)
//        )
//        sceneController.addFigure(plane)

//        val light = Light(Vector3D(800.0, 800.0, 800.0), MaterialColor(255, 255, 255))
//        sceneController.addLight(light)
//        val light2 = Light(Vector3D(800.0, -800.0, 800.0), MaterialColor(255, 255, 255))
//        sceneController.addLight(light2)
//        val light3 = Light(Vector3D(800.0, 0.0, -800.0), MaterialColor(255, 255, 255))
//        sceneController.addLight(light3)
        val camera = Camera(Vector3D(600.0, 0.0, 0.0), -PI / 2, 0.0, PI / 2, 320.0)
//        camera.rotate(z = PI / 4)
        sceneController.addCamera(camera)
        sceneController.setMainCamera(0)
//        controller.setBgColor(MaterialColor(62, 200, 240))

        render()
    }

    fun setMainCamera() {
        mainCamera = activeItem
        sceneController.setMainCamera(mainCamera)
        render()
    }

    fun changeZoom(d: Double) {
        sceneController.changeCameraDist(d)
        render()
    }

    fun moveObject(vec: Vector3D) {
        val (objType, objId) = findObjectByPos()
        when (objType) {
            ObjectType.LIGHT -> sceneController.moveLight(objId, vec).also { println("in move light") }
            ObjectType.FIGURE -> sceneController.moveFigure(objId, vec).also { println("in move figure") }
            ObjectType.CAMERA -> sceneController.moveCamera(objId, vec).also { println("in move camera") }
        }
        render()
    }

    fun rotateObject(ang: Vector3D) {
        val newRotate = Vector3D(
            Math.toRadians(ang.x),
            Math.toRadians(ang.y),
            Math.toRadians(ang.z)
        )
        val (objType, objId) = findObjectByPos()
        when (objType) {
            ObjectType.LIGHT -> sceneController.rotateLight(objId, newRotate).also { println("in rotate light") }
            ObjectType.FIGURE -> sceneController.rotateFigure(objId, newRotate).also { println("in rotate figure") }
            ObjectType.CAMERA -> sceneController.rotateCamera(objId, newRotate).also { println("in rotate camera") }
        }
        render()
    }

    fun addObject(ob: BaseObject) {
        println("in add object fun")
        when (ob) {
            is Light -> sceneController.addLight(ob).also { println("in add light") }
            is BaseComplexFigure -> sceneController.addFigure(ob).also { println("in add figure") }
            is Camera -> sceneController.addCamera(ob).also { println("in add camera") }
            else -> println("undefined object")
        }
        insertObjectsList(ob)
        if (ob !is Camera)
            render()
    }

    fun deleteObject() {
        if (activeItem == mainCamera)
            return

        val (objType, objId) = findObjectByPos()
        when (objType) {
            ObjectType.LIGHT -> sceneController.deleteLight(objId).also { println("in add light") }
            ObjectType.FIGURE -> sceneController.deleteObject(objId).also { println("in add figure") }
            ObjectType.CAMERA -> sceneController.deleteCamera(objId).also { println("in add camera") }
        }

        if (objType != ObjectType.CAMERA)
            render()

        objectsArray.removeAt(activeItem)
    }

    fun clearObjects() {
        sceneController.clearObjects()
        sceneController.clearLights()
        sceneController.clearCameras()
        render()
        objectsArray.clear()
        objectsArray.add("Camera 1")
    }

    fun isCamera(pos: Int) = pos < sceneController.scene.camerasNum

    private fun findObjectByPos(): Pair<ObjectType, Int> {
        val camerasMax = sceneController.scene.camerasNum
        val lightsMax = sceneController.scene.camerasNum + sceneController.scene.lightsNum

        return when  {
            activeItem < camerasMax -> Pair(ObjectType.CAMERA, activeItem)
            activeItem in camerasMax until lightsMax -> Pair(ObjectType.LIGHT, activeItem - camerasMax)
            else -> Pair(ObjectType.FIGURE, activeItem - lightsMax)
        }
    }

    private fun insertObjectsList(ob: BaseObject) {
        val newName: String = when (ob) {
            is Light -> "Light"
            is Camera -> "Camera"
            is Cube -> "Cube"
            is Plane -> "Plane"
            is Prism -> "Prism"
            is Pyramid -> "Pyramid"
            is Sphere -> "Sphere"
            else -> ""
        }
        objectsArray.add(getInsertPosition(ob), "$newName ${getObjectNum(ob)}")
    }


    private fun getObjectNum(ob: BaseObject) = when (ob) {
        is Light -> sceneController.scene.lightsNum
        is Camera -> sceneController.scene.camerasNum
        is BaseComplexFigure -> sceneController.scene.objectsNum
        else -> 0
    }

    private fun getInsertPosition(ob: BaseObject) = when (ob) {
        is Light -> sceneController.scene.lightsNum + sceneController.scene.camerasNum
        is Camera -> sceneController.scene.camerasNum
        is BaseComplexFigure -> sceneController.scene.objectsNum + sceneController.scene.lightsNum + sceneController.scene.camerasNum
        else -> 1
    } - 1


    private fun render() {
        val tmp = _canvas.value!!
        renderController.render(tmp, sceneController.mainCamera, sceneController.scene)
        _canvas.value = tmp
    }

    private companion object {
        const val WIDTH_PERCENT = 0.6
        const val HEIGHT_PERCENT = 0.9
    }
}
