package com.example.viewer.renderer.scene.render

import com.example.viewer.renderer.math.Vector3D
import kotlin.math.abs
import kotlin.math.min

class ShadowModel() {
    private lateinit var shadowBuffers: Array<Array<Array<Double>>>
    var bufferWidth = 0
    var bufferHeight = 0

    constructor(width: Int, height: Int) : this() {
        bufferWidth = width
        bufferHeight = height
        shadowBuffers = Array(6) { Array(bufferHeight) { Array(bufferWidth) { Double.MAX_VALUE } } }
    }

    fun setPixel(bufferIndex: Int, x: Int, y: Int, z: Double) {
        shadowBuffers[bufferIndex][y][x] = z
    }

    fun getDepthByIndex(bufferIndex: Int, x: Int, y: Int) = shadowBuffers[bufferIndex][y][x]

    fun getDepthByVector(vec: Vector3D): Double {
        val absX = abs(vec.x)
        val absY = abs(vec.y)
        val absZ = abs(vec.z)
        val xCond = (absX >= absY) and (absX >= absZ)
        val yCond = (absY >= absX) and (absY >= absZ)
        val zCond = (absZ >= absX) and (absZ >= absY)

        val isXPos = vec.x > 0.0
        val isYPos = vec.y > 0.0
        val isZPos = vec.z > 0.0

        var maxAxis = 0.0
        var index = 0
        var u = 0.0
        var v = 0.0

        if (isXPos and xCond) { // pos x
            maxAxis = absX
            u = -vec.z
            v = vec.y
            index = 0
        } else if (!isXPos and xCond) { // neg x
            maxAxis = absX
            u = vec.z
            v = vec.y
            index = 1
        } else if (isYPos and yCond) { // pos y
            maxAxis = absY
            u = vec.x
            v = -vec.z
            index = 2
        } else if (!isYPos and yCond) { // neg y
            maxAxis = absY
            u = vec.x
            v = vec.z
            index = 3
        } else if (isZPos and zCond) { // pos z
            maxAxis = absZ
            u = vec.x
            v = vec.y
            index = 4
        } else { // neg z
            maxAxis = absZ
            u = -vec.x
            v = vec.y
            index = 5
        }

        // convert from -1..1 to 0..1
        u = 0.5 * (u / maxAxis + 1.0) * bufferWidth
        v = bufferHeight - 0.5 * (v / maxAxis + 1.0) * bufferHeight
        val ui = min(u.toInt(), bufferWidth - 1)
        val vi = min(v.toInt(), bufferHeight - 1)

        return shadowBuffers[index][vi][ui]
    }

    fun clear() {
        shadowBuffers.forEach { arrayOfArrays ->
            arrayOfArrays.forEach { array ->
                array.forEachIndexed { i, _ ->
                    array[i] = Double.MAX_VALUE
                }
            }
        }
    }

}