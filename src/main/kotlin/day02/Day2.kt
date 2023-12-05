package day02

import common.InputReader

public fun main() {
    val lines = InputReader("day02/input.txt").lines()
    val games = lines.map(Game::parse)

    // part 1
    val bag = Bag(12, 13, 14)
    val idSum = games.filter { it.isPossibleWith(bag) }.sumOf { it.id }
    println("Part 1: $idSum")

    // part 2
    val powerSum = games
        .map { it.minimumBag() }
        .sumOf { it.power }
    println("Part 2: $powerSum")
}