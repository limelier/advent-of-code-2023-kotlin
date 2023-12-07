package day06

import common.InputReader
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private fun waysToBeat(time: Long, recordDistance: Long): Long {
    // terms of a quadratic inequality: timePressed^2 - time * timePressed + recordDistance >= 0
    val a = 1L
    val b = -time
    val c = recordDistance

    val delta = sqrt((b*b - 4*a*c).toDouble())
    val firstRoot = (-b - delta) / (2*a)
    val secondRoot = (-b + delta) / (2*a)

    // roots are floats, find all the integers between them
    val solutions = ceil(firstRoot).toLong()..floor(secondRoot).toLong()
    return solutions.last - solutions.first + 1
}
public fun main() {
    val lines = InputReader("day06/input.txt").lines()
    val whitespace = "\\s+".toRegex()

    val times = lines[0].split(whitespace).drop(1).map { it.toLong() }
    val records = lines[1].split(whitespace).drop(1).map { it.toLong() }

    val product = times.zip(records).map { (time, record) -> waysToBeat(time, record)}.reduce(Long::times)
    println("Part 1: $product")

    val realTime = lines[0].filter { it.isDigit() }.toLong()
    val realRecord = lines[1].filter { it.isDigit() }.toLong()

    val waysToBeat = waysToBeat(realTime, realRecord)
    println("Part 2: $waysToBeat")
}