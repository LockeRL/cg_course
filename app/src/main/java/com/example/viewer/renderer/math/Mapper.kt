package com.example.viewer.renderer.math


fun Vector4D.toVector3D(): Vector3D =
    if (w > 0.0f) Vector3D(x / w, y / w, z / w) else Vector3D(x, y, z)

fun Vector3D.toVector4D(w: Double): Vector4D = Vector4D(x, y, z, w)