package day11

import kotlin.math.max
import kotlin.math.min

internal class GalaxyMap(
    inputLines: Iterable<String>
) {
    private val galaxies = inputLines.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, char ->
            if (char == '#') Galaxy(row, col) else null
        }
    }

    val emptyRows = let {
        val galaxyRows = galaxies.map { it.row }.toSortedSet()
        val galaxyRowDomain = galaxyRows.min()..galaxyRows.max()
        (galaxyRowDomain - galaxyRows).toSet()
    }

    val emptyCols = let {
        val galaxyCols = galaxies.map { it.col }.toSortedSet()
        val galaxyColDomain = galaxyCols.min().. galaxyCols.max()
        (galaxyColDomain - galaxyCols).toSet()
    }

    inner class Galaxy(
        val row: Int,
        val col: Int,
    ) {
        /**
         * Calculate the taxicab distance to the [other] galaxy. Each empty row and column in-between will add
         * be counted [gapMultiplier] times.
         */
        fun distanceTo(other: Galaxy, gapMultiplier: Int): Long {
            val minRow = min(row, other.row)
            val maxRow = max(row, other.row)
            val minCol = min(col, other.col)
            val maxCol = max(col, other.col)

            return maxRow - minRow + maxCol - minCol +
                   (minRow..maxRow).intersect(emptyRows).count().toLong() * (gapMultiplier - 1) +
                   (minCol..maxCol).intersect(emptyCols).count().toLong() * (gapMultiplier - 1)
        }
    }

    fun pairwiseDistanceSum(gapMultiplier: Int): Long {
        var sum = 0L
        for (i in galaxies.indices) {
            for (j in i+1..<galaxies.size) {
                sum += galaxies[i].distanceTo(galaxies[j], gapMultiplier)
            }
        }
        return sum
    }
}