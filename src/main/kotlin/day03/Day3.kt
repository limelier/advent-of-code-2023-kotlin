package day03

import common.InputReader

private typealias PartPos = Pair<Int, Int>

public fun main() {
    val matrix = InputReader("day03/input.txt").lines().map { it.toCharArray() }

    // establish bounds
    val rowIndices = matrix.indices
    val colIndices = matrix[0].indices

    var partNumberSum = 0
    var gearRatioSum = 0
    val unmatchedGearNumbers: MutableMap<PartPos, Int> = mutableMapOf()

    for (r in rowIndices) {
        val row = matrix[r]

        var c = 0
        while (c in colIndices) {
            val ch = row[c]
            if (!ch.isDigit()) {
                c++
                continue
            }

            // consume number
            var cPastNumEnd = c + 1
            var numString = ch.toString()
            while (cPastNumEnd in colIndices && row[cPastNumEnd].isDigit()) {
                numString += row[cPastNumEnd]
                cPastNumEnd++
            }
            val number = numString.toInt()

            // scan for symbol in bounding box
            val scanRows = rowIndices.intersect(r-1..r+1)
            val scanCols = colIndices.intersect(c-1..cPastNumEnd)
            var hasSymbol = false
            symbolScan@ for (rScan in scanRows) {
                for (cScan in scanCols) {
                    val chScan = matrix[rScan][cScan]
                    if (chScan == '.' || chScan.isDigit()) {
                        continue
                    }

                    // part 2 segment: handle possible gear numbers
                    if (chScan == '*') {
                        val key = PartPos(rScan, cScan)
                        if (key in unmatchedGearNumbers) {
                            gearRatioSum += unmatchedGearNumbers[key]!! * number
                            unmatchedGearNumbers.remove(key)
                        } else {
                            unmatchedGearNumbers[key] = number
                        }
                    }

                    hasSymbol = true
                    break@symbolScan
                }
            }

            c = cPastNumEnd + 1
            if (hasSymbol) {
                partNumberSum += number
            }
        }
    }

    println("Part 1: $partNumberSum")
    println("Part 2: $gearRatioSum")
}

