package com.example.viewer.renderer.scene.kdtree

import com.example.viewer.renderer.scene.base.BaseFigure
import com.example.viewer.renderer.scene.kdtree.data.Plane

class KDNode {
    var left: KDNode? = null
    var right: KDNode? = null

    var objects: MutableList<BaseFigure> = mutableListOf()

    var coord: Double = 0.0

    var plane: Plane = Plane.NONE
}