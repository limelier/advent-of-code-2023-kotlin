package day01

import common.InputReader

private fun String.asDigit(): Int = when(this) {
    "one", "1" -> 1
    "two", "2" -> 2
    "three", "3" -> 3
    "four", "4" -> 4
    "five", "5" -> 5
    "six", "6" -> 6
    "seven", "7" -> 7
    "eight", "8" -> 8
    "nine", "9" -> 9
    "zero", "0" -> 0
    else -> throw IllegalArgumentException("Expected string '$this' to be a digit or the name of a digit")
}

/**
 * Find the number made up of the first and last digits in the string, identified by the last group of the [regex] match.
 *
 * Overlaps are possible.
 */
private fun String.lineNumber(regex: Regex = """\d""".toRegex()): Int {
    val matches = regex.findAll(this).toList()

    val first = matches.first().groupValues.last().asDigit()
    val last = matches.last().groupValues.last().asDigit()

    return first * 10 + last
}

public fun main() {
    val lines = InputReader("day01/input.txt").lines()

    var sum1 = 0
    var sum2 = 0
    lines.forEach { line ->
        sum1 += line.lineNumber()
        sum2 += line.lineNumber("""(?=(one|two|three|four|five|six|seven|eight|nine|zero|\d))""".toRegex())
    }

    println("Part 1: $sum1")
    println("Part 2: $sum2")
}