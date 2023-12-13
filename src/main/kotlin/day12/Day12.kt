package day12

import common.InputReader
import day12.SpringGroup.Companion.parseSprings

private fun String.multiplyJoining(times: Int, separator: String): String =
    List(times) { this }.joinToString(separator)

public fun main() {
    val lines = InputReader("day12/input.txt").lines().map { it.split(" ") }
    val groups = lines.map { parseSprings(it[0], it[1]) }

    println("Part 1: ${groups.sumOf { it.waysToComplete() }}")
    println("Cache entries: ${SpringGroup.waysToCompleteCache.size}")

    val groups2 = lines.map { (text, numbers) ->
        val t = text.multiplyJoining(5, "?")
        val n = numbers.multiplyJoining(5, ",")
        parseSprings(t, n)
    }

    println("Part 2: ${groups2.sumOf { it.waysToComplete() }}")
    println("Cache entries: ${SpringGroup.waysToCompleteCache.size}")
}