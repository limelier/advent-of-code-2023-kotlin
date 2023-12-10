package day10.tile

internal enum class Direction {
    North,
    East,
    South,
    West;

    val opposite get() = when(this) {
        North -> South
        East -> West
        South -> North
        West -> East
    }
}