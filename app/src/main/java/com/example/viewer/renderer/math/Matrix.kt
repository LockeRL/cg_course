package com.example.viewer.renderer.math

import kotlin.math.cos
import kotlin.math.sin

private const val ZERO = 0.0

class Matrix(
    val rows: Int,
    val cols: Int,
    private val data: DoubleArray = DoubleArray(rows * cols) { ZERO }
) {
    operator fun get(i: Int, j: Int) = data[index(i, j)]

    operator fun set(i: Int, j: Int, a: Double) {
        data[index(i, j)] = a
    }

    operator fun plus(other: Matrix) = Matrix(
        rows,
        cols,
        DoubleArray(rows * cols) { i ->
            data[i] + other.data[i]
        })

    operator fun plusAssign(other: Matrix) {
        data.forEachIndexed { i, _ ->
            data[i] += other.data[i]
        }
    }

    operator fun times(other: Matrix): Matrix {
        val newData = DoubleArray(rows * other.cols) {
            val rowIndex = it / other.cols
            val colIndex = it % other.cols
            var sum = 0.0
            for (i in 0 until this.cols) {
                sum += this[rowIndex, i] * other[i, colIndex]
            }
            sum
        }

        return Matrix(rows, cols, newData)
    }

    operator fun timesAssign(other: Matrix) {
        val newData = DoubleArray(rows * other.cols) {
            val rowIndex = it / other.cols
            val colIndex = it % other.cols
            var sum = 0.0
            for (i in 0 until this.cols) {
                sum += this[rowIndex, i] * other[i, colIndex]
            }
            sum
        }

        data.forEachIndexed { i, _ ->
            data[i] = newData[i]
        }
    }

    operator fun minus(other: Matrix) = Matrix(
        rows,
        cols,
        DoubleArray(rows * cols) { i ->
            data[i] - other.data[i]
        })

    operator fun minusAssign(other: Matrix) {
        data.forEachIndexed { i, _ ->
            data[i] -= other.data[i]
        }
    }

    fun transpose(): Matrix {
        val newData = DoubleArray(rows * cols) { i ->
            this[i % rows, i / rows]
        }
        return Matrix(cols, rows, newData)
    }

    fun isZero(): Boolean {
        data.forEach { item ->
            if (item != ZERO)
                return false
        }
        return true
    }


    fun inverse() {
        val d = det()
        val res = DoubleArray(rows * cols) { ZERO }
        val buf = Matrix(rows - 1, cols - 1)

        for (i in 0 until rows)
            for (j in 0 until cols) {
                excludeCopy(buf, this, i, j)
                var a = buf.det() / d
                if ((i + j) % 2 == 1)
                    a = -a
                res[index(i, j)] = a
            }
        data.forEachIndexed { i, _ ->
            data[i] = res[i]
        }
    }

    fun invertedMatrix(): Matrix {
        val d = det()
        val res = DoubleArray(rows * cols) { ZERO }
        val buf = Matrix(rows - 1, cols - 1)

        for (i in 0 until rows)
            for (j in 0 until cols) {
                excludeCopy(buf, this, i, j)
                var a = buf.det() / d
                if ((i + j) % 2 == 1)
                    a = -a
                res[index(i, j)] = a
            }

        return Matrix(rows, cols, res)
    }

    fun det(): Double {
        if (cols == 1) return data[0]
        if (cols == 2) return data[0] * data[3] - data[1] * data[2]

        val tmp = Matrix(cols - 1, rows - 1)
        var res = 0.0
        for (i in 0 until rows) {
            excludeCopy(tmp, this, 0, i)
            var minor = tmp.det()
            if (i % 2 == 1)
                minor = -minor
            res += minor * data[i]
        }
        return res
    }

    private fun excludeCopy(target: Matrix, src: Matrix, exRow: Int, exCol: Int) {
        var row = 0
        var col = 0
        for (i in 0 until src.rows - 1)
            for (j in 0 until src.cols - 1) {
                row = if (i >= exRow) i + 1 else i
                col = if (j >= exCol) j + 1 else j
                target[i, j] = src[row, col]
            }
    }

    fun adjoint(): Matrix {
        val newData = DoubleArray(rows * cols) { i ->
            val row = i / cols
            val col = i % cols
            val sign = if ((row + col) % 2 == 1) -1.0f else 1.0f
            val cofactorDet = this.minorMatrix(row, col).det()
            sign * cofactorDet
        }
        return Matrix(rows, cols, newData).transpose()
    }

    private fun minorMatrix(rowIndex: Int, colIndex: Int): Matrix {
        val newData = DoubleArray((rows - 1) * (cols - 1)) { i ->
            var cofRow = i / (cols - 1)
            var cofCol = i % (cols - 1)
            if (cofRow >= rowIndex) cofRow += 1
            if (cofCol >= colIndex) cofCol += 1
            this[cofRow, cofCol]
        }
        return Matrix(rows - 1, cols - 1, newData)
    }

    fun copy() = Matrix(rows, cols, data.copyOf())

    private fun index(i: Int, j: Int) = i * cols + j

    companion object {
        const val MATRIX_SIZE = 4

        fun identityMatrix(n: Int): Matrix {
            val newData = DoubleArray(n * n) {
                val rowIndex = it / n
                val colIndex = it % n
                if (rowIndex == colIndex) 1.0 else 0.0
            }
            return Matrix(n, n, newData)
        }

        fun rotateXMatrix(angle: Double) = identityMatrix(4).apply {
            val rad = Math.toRadians(angle)
            this[1, 1] = cos(rad)
            this[1, 2] = sin(rad)
            this[2, 1] = -this[1, 2]
            this[2, 2] = this[1, 1]
        }

        fun rotateYMatrix(angle: Double) = identityMatrix(4).apply {
            val rad = Math.toRadians(angle)
            this[0, 0] = cos(rad)
            this[0, 2] = -sin(rad)
            this[2, 0] = -this[0, 2]
            this[2, 2] = this[0, 0]
        }

        fun rotateZMatrix(angle: Double) = identityMatrix(4).apply {
            val rad = Math.toRadians(angle)
            this[0, 0] = cos(rad)
            this[0, 1] = sin(rad)
            this[1, 0] = -this[0, 1]
            this[1, 1] = this[0, 0]
        }

    }

}