package com.example.viewer.renderer.scene.kdtree

import com.example.viewer.renderer.scene.base.BaseFigure

class KDNode {
    var left: KDNode? = null
    var right: KDNode? = null

    var objects: MutableList<BaseFigure> = mutableListOf()
    val objectsCount get() = objects.size

    var coord: Double = 0.0

    var plane: Plane = Plane.NONE
}