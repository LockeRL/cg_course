package com.example.viewer.renderer


import android.widget.EditText


const val EPS = 1e-5

fun EditText.decimal() = text.toString().toDouble()

fun EditText.int() = text.toString().toInt()

fun Boolean.toInt() = if (this) 1 else 0