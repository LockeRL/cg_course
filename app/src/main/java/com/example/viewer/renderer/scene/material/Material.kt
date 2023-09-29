package com.example.viewer.renderer.scene.material

data class Material(
    // ka + kd + ks + kr + kt = 1.0
    var ka: Double = 0.0, // ambient
    var kd: Double = 1.0, // diffuse
    var ks: Double = 0.0, // specular
    var kr: Double = 0.0, // reflection
    var kt: Double = 0.0, // transparency
    var p: Double = 0.0,
    val status: Boolean = true
) {
    constructor(ka: Double, kd: Double, ks: Double, kr: Double, kt: Double, p: Double): this() {
        val sum = ka + kd + ks + kr + kt
        this.ka = ka / sum
        this.kd = kd / sum
        this.ks = ks / sum
        this.kr = kr / sum
        this.kt = kt / sum
        this.p = p
    }

    fun set(other: Material) {
        ka = other.ka
        kd = other.kd
        ks = other.ks
        kr = other.kr
        kt = other.kt
        p = other.p
    }
}
