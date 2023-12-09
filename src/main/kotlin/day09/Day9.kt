package day09

import common.InputReader

/** Find the previous and next elements in the sequence */
private fun extrapolate(sequence: List<Int>): Pair<Int, Int> {
    if (sequence.all { it == 0 }) return 0 to 0

    val differences = sequence.zip(sequence.drop(1)).map { it.second - it.first }
    val extDiffs = extrapolate(differences)
    return sequence.first() - extDiffs.first to sequence.last() + extDiffs.second
}

public fun main() {
    val sequences = InputReader("day09/input.txt").lines()
        .map { line ->
            line.split(" ")
                .map { it.toInt() }
        }

    val extrapolated = sequences.map(::extrapolate)

    println("Part 1: ${extrapolated.sumOf { it.second }}")
    println("Part 2: ${extrapolated.sumOf { it.first }}")
}