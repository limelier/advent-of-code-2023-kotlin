package day10

import common.InputReader
import day10.tile.PipeTile.Companion.pipeTile
import day10.tile.EmptyTile
import day10.tile.StartTile

public fun main() {
    val tiles = InputReader("day10/input.txt")
        .lines()
        .map { line -> line.map {
            when (it) {
                'S' -> StartTile
                '.' -> EmptyTile
                else -> it.pipeTile()
            }
        }.toTypedArray() }.toTypedArray()
        .let { PipeMap(it) }

    println("Part 1: ${tiles.mainLoop.area / 2 - 1}")
    println("Part 2: ${tiles.insideMainLoop.area}")

    for (r in tiles.rows) {
        for (c in tiles.cols) {
            if (tiles.mainLoop.map[r][c]) {
                print("\u001b[31m")  // red text
            }
            if (tiles.insideMainLoop.map[r][c]) {
                print("\u001b[42m")  // green background
            }

            print(tiles.matrix[r][c])
            print("\u001b[0m")  // reset
        }
        println()
    }
}