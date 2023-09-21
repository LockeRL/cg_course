package com.example.viewer.renderer.scene.light.data

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.MaterialColor

data class LightAttributes(val position: Vector3D = Vector3D(), var color: MaterialColor)
