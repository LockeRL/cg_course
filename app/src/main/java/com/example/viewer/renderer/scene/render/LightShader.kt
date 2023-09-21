package com.example.viewer.renderer.scene.render

import com.example.viewer.renderer.math.Matrix
import com.example.viewer.renderer.math.Vector3D
import com.example.viewer.renderer.math.Vector4D
import com.example.viewer.renderer.math.toVector4D
import com.example.viewer.renderer.scene.MaterialColor
import com.example.viewer.renderer.scene.base.BaseShader
import java.util.Vector

class LightShader() : BaseShader() {

    override fun geometry(triangle: Array<Vector4D>) {}

    override fun fragment(barycentric: Vector3D, shadowModel: ShadowModel): MaterialColor =
        lightColor

}