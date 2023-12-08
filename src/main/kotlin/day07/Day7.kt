package day07

import common.InputReader

private fun Map<Hand, Int>.totalScore(joker: Boolean): Int =
    this.toSortedMap(Hand.comparator(joker))
        .values
        .mapIndexed { index, bet -> (index + 1) * bet}
        .sum()


public fun main() {
    val lines = InputReader("day07/input.txt").lines()

    val hands = lines
        .map { it.split(" ")}
        .associate { Hand(it[0]) to it[1].toInt() }

    println("Part 1: ${hands.totalScore(false)}")
    println("Part 2: ${hands.totalScore(true)}")
}