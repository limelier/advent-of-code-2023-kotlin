package day08.map

internal enum class Direction{
    Left,
    Right,
}

internal fun Char.toDirection(): Direction = when (this) {
    'L' -> Direction.Left
    'R' -> Direction.Right
    else -> throw IllegalArgumentException("Can't convert '$this' to a direction!")
}