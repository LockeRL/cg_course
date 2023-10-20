package com.example.viewer.renderer.scene.kdtree

import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive
import com.example.viewer.renderer.scene.kdtree.data.Plane

class KDNode {
    var left: KDNode? = null
    var right: KDNode? = null

    var objects: MutableList<BasePrimitive> = mutableListOf()

    var coord: Double = 0.0

    var plane: Plane = Plane.NONE
}