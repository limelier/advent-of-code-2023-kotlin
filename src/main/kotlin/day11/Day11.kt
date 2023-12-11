package day11

import common.InputReader

internal fun main() {
    val galaxies = GalaxyMap(InputReader("day11/input.txt").lines())

    println("Part 1: ${galaxies.pairwiseDistanceSum(2)}")
    println("Part 2: ${galaxies.pairwiseDistanceSum(1_000_000)}")
}