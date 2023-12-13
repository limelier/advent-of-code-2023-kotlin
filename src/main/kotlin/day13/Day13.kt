package day13

import common.InputReader
import common.split

private fun List<String>.checkMirroredHorizontal(rowsBefore: Int): Boolean {
    for (row in rowsBefore..<this.size) {
        val rowMir = rowsBefore * 2 - row - 1
        if (rowMir !in indices) break
        if (this[row] != this[rowMir]) return false
    }
    return true
}

private fun List<String>.checkMirroredHorizontalWithSmudge(rowsBefore: Int): Boolean {
    var smudge = false
    for (row in rowsBefore..<this.size) {
        val rowMir = rowsBefore * 2 - row - 1
        if (rowMir !in indices) break
        for (col in this[row].indices) {
            if (this[row][col] != this[rowMir][col]) {
                if (!smudge) {
                    smudge = true
                } else {
                    return false
                }
            }
        }

    }
    return smudge
}

private fun List<String>.transpose(): List<String> = this[0].indices.map { c -> this.map { it[c] }.joinToString() }

public fun main() {
    val lines = InputReader("day13/input.txt").lines()
    val patterns = lines.split("")

    val sum1 = patterns.sumOf { pattern ->
        val rowsBeforeMirror = (1..<pattern.size).firstOrNull { pattern.checkMirroredHorizontal(it) }
        val transposed = pattern.transpose()
        val colsBeforeMirror = (1..<transposed.size).firstOrNull { transposed.checkMirroredHorizontal(it) }

        colsBeforeMirror ?: (rowsBeforeMirror!! * 100)
    }
    println("Part 1: $sum1")

    val sum2 = patterns.sumOf { pattern ->
        val rowsBeforeMirror = (1..<pattern.size).firstOrNull { pattern.checkMirroredHorizontalWithSmudge(it) }
        val transposed = pattern.transpose()
        val colsBeforeMirror = (1..<transposed.size).firstOrNull { transposed.checkMirroredHorizontalWithSmudge(it) }

        colsBeforeMirror ?: (rowsBeforeMirror!! * 100)
    }
    println("Part 2: $sum2")
}