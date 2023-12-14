package day14

import common.InputReader
import util.Grid
import util.Grid.Companion.asGrid

private enum class Rock {
    Round,
    Square,
    None;

    override fun toString(): String = when(this) {
        Round -> "O"
        Square -> "#"
        None -> "."
    }
}

private fun Grid<Rock>.rollNorth() {
    for (lastRow in rowIndices.reversed()) {
        for (row in 1 .. lastRow) {
            for (col in colIndices) {
                val tile = this[row, col]
                if (tile == Rock.Round && this[row - 1, col] == Rock.None) {
                    this[row - 1, col] = Rock.Round
                    this[row, col] = Rock.None
                }
            }
        }
    }
}

private fun Grid<Rock>.rollSouth() {
    for (firstRow in rowIndices) {
        for (row in rowCount - 2 downTo firstRow) {
            for (col in colIndices) {
                val tile = this[row, col]
                if (tile == Rock.Round && this[row + 1, col] == Rock.None) {
                    this[row + 1, col] = Rock.Round
                    this[row, col] = Rock.None
                }
            }
        }
    }
}

private fun Grid<Rock>.rollEast() {
    for (firstCol in colIndices) {
        for (col in colCount - 2 downTo firstCol) {
            for (row in rowIndices) {
                val tile = this[row, col]
                if (tile == Rock.Round && this[row, col + 1] == Rock.None) {
                    this[row, col + 1] = Rock.Round
                    this[row, col] = Rock.None
                }
            }
        }
    }
}

private fun Grid<Rock>.rollWest() {
    for (lastCol in colIndices.reversed()) {
        for (col in 1..lastCol) {
            for (row in rowIndices) {
                val tile = this[row, col]
                if (tile == Rock.Round && this[row, col - 1] == Rock.None) {
                    this[row, col - 1] = Rock.Round
                    this[row, col] = Rock.None
                }
            }
        }
    }
}

private fun Grid<Rock>.spinCycle() {
    rollNorth()
    rollWest()
    rollSouth()
    rollEast()
}

private fun Grid<Rock>.load(): Int = rowIndices.sumOf { r ->
    this[r].count { it == Rock.Round } * (rowCount - r)
}

public fun main() {
    val rocks = InputReader("day14/input.txt")
        .lines()
        .asGrid { line -> line.map { when(it) {
            'O' -> Rock.Round
            '#' -> Rock.Square
            '.' -> Rock.None
            else -> throw IllegalArgumentException("Bad input")
        } }.toTypedArray() }

    rocks.rollNorth()

    println("Part 1: ${rocks.load()}")

    val copies = mutableListOf(Grid.shallowCopy(rocks))
    var iEqualToLast = 0
    for (cycle in 1..1_000_000_000) {
        rocks.spinCycle()

        val equalCopyIdx = copies.indexOfFirst { it == rocks }
        if (equalCopyIdx != -1) {
            println("Rocks after $cycle cycles are the same as after $equalCopyIdx cycles, breaking")
            iEqualToLast = equalCopyIdx
            break
        }

        copies.add(Grid.shallowCopy(rocks))
    }

    // we now have a cycle: rocks == copies[iEqualToLast]
    val cycleLength = copies.size - iEqualToLast
    val cycledCycles = 1_000_000_000 - iEqualToLast
    val finalCopyIdx = iEqualToLast + cycledCycles % cycleLength
    copies[finalCopyIdx].print("")

    println("Part 2: ${copies[finalCopyIdx].load()}")
}