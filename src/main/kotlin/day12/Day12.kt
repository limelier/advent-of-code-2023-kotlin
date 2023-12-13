package day12

import common.InputReader
import day12.SpringGroup.Companion.parseSprings

public fun main() {
    val groups = InputReader("day12/input.txt")
        .lines()
        .map { it.parseSprings() }

    println("Part 1: ${groups.sumOf { it.waysToComplete() }}")
}