package day10

import common.InputReader
import day10.tile.PipeTile.Companion.pipeTile
import day10.tile.EmptyTile
import day10.tile.StartTile
import util.Grid.Companion.asGrid

public fun main() {
    val pipeMap = InputReader("day10/input.txt")
        .lines()
        .asGrid { line ->
            line.map {
                when (it) {
                    'S' -> StartTile
                    '.' -> EmptyTile
                    else -> it.pipeTile()
                }
            }.toTypedArray()
        }.let { PipeMap(it) }

    println("Part 1: ${pipeMap.mainLoop.area / 2 - 1}")
    println("Part 2: ${pipeMap.insideMainLoop.area}")

    pipeMap.grid.print("") { r, c, tile ->
        buildString {
            if (pipeMap.mainLoop.map[r, c]) {
                append("\u001b[31m") // red text
            }
            if (pipeMap.insideMainLoop.map[r, c]) {
                append("\u001b[42m") // green background
            }
            append(tile)
            append("\u001b[0m") // reset
        }
    }
}