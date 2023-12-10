package day10.tile

import day10.tile.Direction.*

internal enum class PipeTile : Tile {
    NorthSouth,
    EastWest,
    NorthEast,
    SouthEast,
    SouthWest,
    NorthWest;

    companion object {

        fun Char.pipeTile(): PipeTile = when(this) {
            '|' -> NorthSouth
            '-' -> EastWest
            'L' -> NorthEast
            'F' -> SouthEast
            '7' -> SouthWest
            'J' -> NorthWest
            else -> throw IllegalArgumentException("Illegal tile character")
        }
    }

    val connections: List<Direction> get() = when (this) {
        NorthSouth -> listOf(North, South)
        EastWest -> listOf(East, West)
        NorthEast -> listOf(North, East)
        SouthEast -> listOf(South, East)
        SouthWest -> listOf(South, West)
        NorthWest -> listOf(North, West)
    }

    override fun toString(): String = when(this) {
        NorthSouth -> "│"
        EastWest -> "─"
        NorthEast -> "└"
        SouthEast -> "┌"
        SouthWest -> "┐"
        NorthWest -> "┘"
    }
}