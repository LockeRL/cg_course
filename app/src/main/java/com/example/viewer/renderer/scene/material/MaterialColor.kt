package com.example.viewer.renderer.scene.material

import android.graphics.Color


class MaterialColor(var red: Int = 0, var green: Int = 0, var blue: Int = 0, var alpha: Int = MAX_COMPONENT_VAL) {

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

    operator fun minusAssign(other: MaterialColor) {
        red = componentMinus(red, other.red)
        green = componentMinus(green, other.green)
        blue = componentMinus(blue, other.blue)
    }

    operator fun minus(other: MaterialColor) = MaterialColor(
        red = componentMinus(red, other.red),
        green = componentMinus(green, other.green),
        blue = componentMinus(blue, other.blue)
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
        red = componentTimesFloat(red, double)
        green = componentTimesFloat(green, double)
        blue = componentTimesFloat(blue, double)
    }

    operator fun times(double: Double) = MaterialColor(
        red = componentTimesFloat(red, double),
        green = componentTimesFloat(green, double),
        blue = componentTimesFloat(blue, double)
    )

    fun toArgb(): Int {
        return Color.argb(alpha, red, green, blue)
    }

    fun set(other: MaterialColor) {
        red = other.red
        green = other.green
        blue = other.blue
    }


    private fun componentTimesInt(src: Int, other: Int): Int {
        return minOf(src * other / (MAX_COMPONENT_VAL + 1), MAX_COMPONENT_VAL)
    }

    private fun componentTimesFloat(src: Int, other: Double): Int {
        return minOf((src * other).toInt(), MAX_COMPONENT_VAL)
    }

    private fun componentPlus(src: Int, other: Int): Int {
        return minOf(src + other, MAX_COMPONENT_VAL)
    }

    private fun componentMinus(src: Int, other: Int): Int {
        return maxOf(src - other, MIN_COMPONENT_VAL)
    }

    companion object {
        private const val MAX_COMPONENT_VAL = 255
        private const val MIN_COMPONENT_VAL = 0
    }

}