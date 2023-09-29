package com.example.viewer.renderer.scene.objects

import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.base.BaseObject

class Light(position: Vector3D, var color: MaterialColor) :
    BaseObject(position) { }