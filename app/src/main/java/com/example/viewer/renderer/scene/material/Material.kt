package com.example.viewer.renderer.scene.material

class Material(
    // ka + kd + ks + kr = 1.0
    var ka: Double = 4.0, // ambient
    var kd: Double = 5.0, // diffuse
    var ks: Double = 5.0, // specular
    var kr: Double = 3.0, // reflection
    var p: Double = 10.0
) {

    init {
        val sum = ka + kd + ks + kr
        ka /= sum
        kd /= sum
        ks /= sum
        kr /= sum
    }

    fun set(other: Material) {
        ka = other.ka
        kd = other.kd
        ks = other.ks
        kr = other.kr
        p = other.p
    }
}
