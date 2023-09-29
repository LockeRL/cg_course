package com.example.viewer.renderer.scene.kdtree

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.base.BaseFigure

data class FindIntersectionNodeAnswer(
    var nearestObject: BaseFigure? = null,
    var nearestPoint: Vector3D? = null,
    var nearestPointDist: Double = 0.0,
    var status: Boolean
)
