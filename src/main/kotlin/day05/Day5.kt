package day05

import common.InputReader
import common.split
import day05.Domain.Companion.domain
import day05.Interval.Companion.startInterval

public fun main() {
    val lines = InputReader("day05/input.txt").lines()

    val seeds = lines[0]
        .removePrefix("seeds: ")
        .split(" ")
        .map { it.toLong() }
    val seedDomain = seeds
        .chunked(2)
        .map { it[0].startInterval(it[1]) }
        .domain()

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

    val locations1 = seeds.map { seed ->
        var value = seed
        for (map in maps) {
            value = map.apply(value)
        }
        value
    }
    println("Part 1: ${locations1.min()}")

    var workingDomain = seedDomain
    for (map in maps) {
        workingDomain = map.apply(workingDomain)
    }
    println("Part 2: ${workingDomain.minimum}")
}