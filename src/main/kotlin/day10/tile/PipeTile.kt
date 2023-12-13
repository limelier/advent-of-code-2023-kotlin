package day10.tile

import util.Direction

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
        NorthSouth -> listOf(Direction.N, Direction.S)
        EastWest -> listOf(Direction.E, Direction.W)
        NorthEast -> listOf(Direction.N, Direction.E)
        SouthEast -> listOf(Direction.S, Direction.E)
        SouthWest -> listOf(Direction.S, Direction.W)
        NorthWest -> listOf(Direction.N, Direction.W)
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