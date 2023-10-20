package com.example.viewer.renderer.scene.kdtree.data

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.objects.figure.base.BasePrimitive

data class FindIntersectionNodeAnswer(
    var nearestObject: BasePrimitive? = null,
    var nearestPoint: Vector3D? = null,
    var nearestPointDist: Double = 0.0,
    var status: Boolean
)
