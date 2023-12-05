package day05

import common.InputReader
import common.split

public fun main() {
    val lines = InputReader("day05/input.txt").lines()

    val seeds = lines[0]
        .removePrefix("seeds: ")
        .split(" ")
        .map { it.toLong() }

    val maps = lines
        .drop(2)
        .split("")
        .map { entries ->
            entries.drop(1)
                .map { entry ->
                    val (dest, src, size) = entry.split(" ").map { it.toLong() }
                    AlmanacMap.Entry(dest, src, size)
                }
                .let { AlmanacMap(it) }
        }

    val locations = seeds.map { seed ->
        var value = seed
        for (map in maps) {
            value = map.get(value)
        }
        value
    }

    println("Part 1: ${locations.min()}")
}