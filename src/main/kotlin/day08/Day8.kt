package day08

import common.InputReader
import day08.map.Fork
import day08.map.GhostMap
import day08.map.toDirection
import kotlin.time.measureTime

public fun main() {
    val lines = InputReader("day08/input.txt").lines()
    val instructions = lines[0].map { it.toDirection() }
    val mapLineRegex = """(?<from>\w+) = \((?<left>\w+), (?<right>\w+)\)""".toRegex()
    val map = lines
        .drop(2)
        .associate { line ->
            val match = mapLineRegex.matchEntire(line)!!
            match.groups["from"]!!.value to (Fork(match.groups["left"]!!.value, match.groups["right"]!!.value))
        }
        .let { GhostMap(instructions, it) }

    measureTime {
        val steps = map.Pin("AAA", 0)
            .travel { it.label == "ZZZ" }!!
            .steps
        println("Part 1: $steps")
    }.run { println(" It took $this!") }
    measureTime {
        val startPins = map.nodes.keys
            .filter { it.endsWith('A') }
            .map { map.Pin(it, 0) }
        val finalPins = map.moveAllToZ(startPins)
        println("Part 2: ${finalPins.first().steps}")
    }.run { println(" It took $this!") }
}