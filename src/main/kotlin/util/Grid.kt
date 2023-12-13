package util

import util.Grid.Companion.Deltas.delta
import kotlin.math.abs
import kotlin.math.sqrt

internal class Grid<T>(
    private val data: Array<Array<T>>
) : Iterable<T>, Sequence<T> {
    companion object {
        inline fun <reified T> Iterable<String>.linesAsGrid(
            lineMapper: (String) -> Array<T>
        ): Grid<T> = Grid(this.map(lineMapper).toTypedArray())

        inline fun <reified T> transpose(grid: Grid<T>): Grid<T> {
            val arr = grid.colIndices.map { c ->
                grid.rowIndices.map { r -> grid[r][c] }.toTypedArray()
            }.toTypedArray()
            return Grid(arr)
        }

        data class Pos (
            val r: Int,
            val c: Int,
        ) {
            val int2 get() = Int2(r, c)
            fun Int2.pos() = Pos(x, y)

            operator fun plus(o: Pos): Pos = Pos(r + o.r, c + o.c)

            operator fun minus(o: Pos): Pos = Pos(r - o.r, c - o.c)

            operator fun times(factor: Int): Pos = Pos(r * factor, c * factor)

            infix fun manhattanTo(o: Pos): Int = abs(r - o.r) + abs(c - o.c)

            infix fun cartesianTo(o: Pos): Double =
                (this - o).let { sqrt((it.r * it.r + it.c * it.c).toDouble()) }

            fun orthoNeighbors(): List<Pos> = Cardinal.orthogonal.map { this + it.delta() }
            fun diagNeighbors(): List<Pos> = Cardinal.diagonal.map { this + it.delta() }
            fun eightNeighbors(): List<Pos> = Cardinal.entries.map { this + it.delta() }
        }

        object Deltas {
            val Up = Pos(-1, 0)
            val Down = Pos(1, 0)
            val Left = Pos(0, -1)
            val Right = Pos(0, 1)
            val UpLeft = Up + Left
            val UpRight = Up + Right
            val DownLeft = Down + Left
            val DownRight = Down + Right

            fun Cardinal.delta() = when(this) {
                Cardinal.N -> Up
                Cardinal.NE -> UpRight
                Cardinal.E -> Right
                Cardinal.SE -> DownRight
                Cardinal.S -> Down
                Cardinal.SW -> DownLeft
                Cardinal.W -> Left
                Cardinal.NW -> UpLeft
            }
        }
    }

    val rowCount = data.size
    val rowIndices = data.indices
    val colCount = data[0].size
    val colIndices = data[0].indices

    fun positions(): Iterable<Pos> = rowIndices.flatMap { r -> colIndices.map { c -> Pos(r, c)}}

    operator fun get(r: Int): Array<T> = data[r]
    operator fun get(r: Int, c: Int): T = data[r][c]
    operator fun get(pos: Pos): T = data[pos.r][pos.c]

    operator fun set(r: Int, c: Int, value: T) { this[r, c] = value }
    operator fun set(pos: Pos, value: T) = set(pos.r, pos.c, value)

    override fun iterator(): Iterator<T> = data.flatten().iterator()

    operator fun contains(pos: Pos): Boolean = pos.r in rowIndices && pos.c in colIndices
    
    fun cull(positions: Iterable<Pos>): Iterable<Pos> = positions.filter { it in this }
}