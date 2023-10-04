package com.example.viewer.renderer.scene.material

import android.graphics.Color

class MaterialColor(
    var red: Int = MIN_COMPONENT_VAL,
    var green: Int = MIN_COMPONENT_VAL,
    var blue: Int = MIN_COMPONENT_VAL,
    var alpha: Int = MAX_COMPONENT_VAL
) {

    operator fun plusAssign(other: MaterialColor) {
        red = componentPlus(red, other.red)
        green = componentPlus(green, other.green)
        blue = componentPlus(blue, other.blue)
    }

    operator fun plus(other: MaterialColor) = MaterialColor(
        red = componentPlus(red, other.red),
        green = componentPlus(green, other.green),
        blue = componentPlus(blue, other.blue)
    )

    operator fun timesAssign(other: MaterialColor) {
        red = componentTimesInt(red, other.red)
        green = componentTimesInt(green, other.green)
        blue = componentTimesInt(blue, other.blue)
    }

    operator fun times(other: MaterialColor) = MaterialColor(
        red = componentTimesInt(red, other.red),
        green = componentTimesInt(green, other.green),
        blue = componentTimesInt(blue, other.blue)
    )

    operator fun timesAssign(double: Double) {
        red = componentTimesDouble(red, double)
        green = componentTimesDouble(green, double)
        blue = componentTimesDouble(blue, double)
    }

    operator fun times(double: Double) = MaterialColor(
        red = componentTimesDouble(red, double),
        green = componentTimesDouble(green, double),
        blue = componentTimesDouble(blue, double)
    )

    fun toArgb(): Int {
        return Color.argb(alpha, red, green, blue)
    }

    fun set(other: MaterialColor) {
        red = other.red
        green = other.green
        blue = other.blue
    }

    fun print() {
        println("color: (${red}, ${green}, ${blue})")
    }

    private fun componentTimesInt(src: Int, other: Int) = (src * other) shr 8

    private fun componentTimesDouble(src: Int, other: Double) = (src * other).toInt()

    private fun componentPlus(src: Int, other: Int) = minOf(src + other, MAX_COMPONENT_VAL)

    companion object {
        private const val MAX_COMPONENT_VAL = 255
        private const val MIN_COMPONENT_VAL = 0
    }

}