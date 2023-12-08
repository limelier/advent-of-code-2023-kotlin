package day08.map

internal data class Fork(
    val left: Label,
    val right: Label,
) {
    fun go(dir: Direction): Label = when(dir) {
        Direction.Left -> left
        Direction.Right -> right
    }
}