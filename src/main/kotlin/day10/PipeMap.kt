package day10

import day10.tile.Direction
import day10.tile.EmptyTile
import day10.tile.PipeTile
import day10.tile.StartTile
import day10.tile.Tile

internal class PipeMap(
    val matrix: Array<Array<Tile>>
) {

    companion object {
        class Bitmap(
            val area: Int,
            val map: Array<Array<Boolean>>
        )

        data class Pos(val row: Int, val col: Int) {
            fun adjacent(direction: Direction): Pos = when(direction) {
                Direction.North -> Pos(row - 1, col)
                Direction.East -> Pos(row, col + 1)
                Direction.South -> Pos(row + 1, col)
                Direction.West -> Pos(row, col - 1)
            }
        }

        operator fun <T> Array<Array<T>>.get(pos: Pos) = this[pos.row][pos.col]
        operator fun <T> Array<Array<T>>.set(pos: Pos, value: T) {
            this[pos.row][pos.col] = value
        }
    }

    val rows = matrix.indices
    val cols = matrix[0].indices

    private val startPos: Pos = let {
        outer@ for (r in rows) {
            for (c in cols) {
                if (matrix[r][c] is StartTile) {
                    val pos = Pos(r, c)

                    // replace start tile with the right pipe tile
                    val conns = connections(pos).keys
                    matrix[r][c] = PipeTile.entries.first { it.connections.containsAll(conns) }

                    return@let pos
                }
            }
        }
        throw IllegalStateException("Cannot have a pipe map with no start position")
    }

    val mainLoop: Bitmap by lazy {
        val visited = Array(matrix.size) { Array(matrix[0].size) { false } }
        val unvisited = ArrayDeque<Pos>()

        unvisited.add(startPos)

        var tilesVisited = 0
        while (unvisited.isNotEmpty()) {
            val pos = unvisited.removeFirst()
            val connections = connections(pos)
            for (nPos in connections.values) {
                if (!visited[nPos]) {
                    unvisited += nPos
                }
            }
            visited[pos] = true
            tilesVisited++
        }

        Bitmap(tilesVisited + 1, visited)
    }

    val insideMainLoop: Bitmap by lazy {
        val tilesInside = Array(matrix.size) { Array(matrix[0].size) { false } }
        var area = 0

        for (r in rows) {
            var inside = false
            var segmentEnteredFrom: Direction? = null
            for (c in cols) {
                val tile = matrix[r][c]
                val onMainLoop = mainLoop.map[r][c]

                if (onMainLoop) {
                    when (tile as PipeTile) {
                        PipeTile.NorthSouth -> inside = !inside
                        PipeTile.NorthEast -> segmentEnteredFrom = Direction.North
                        PipeTile.SouthEast -> segmentEnteredFrom = Direction.South
                        PipeTile.NorthWest -> {
                            if (segmentEnteredFrom == Direction.South) inside = !inside
                            segmentEnteredFrom = null
                        }
                        PipeTile.SouthWest -> {
                            if (segmentEnteredFrom == Direction.North) inside = !inside
                            segmentEnteredFrom = null
                        }
                        PipeTile.EastWest -> assert(segmentEnteredFrom != null)
                    }

                    continue
                }

                if (inside) {
                    area++
                    tilesInside[r][c] = true
                }
            }
        }

        Bitmap(area, tilesInside)
    }

    private fun connections(pos: Pos): Map<Direction, Pos> {
        val connections = when(val tile = matrix[pos]) {
            is EmptyTile -> listOf()
            is PipeTile -> tile.connections
            is StartTile -> Direction.entries
        }
        val neighbors = connections.associateWith { pos.adjacent(it) }
            .filterValues { it.row in rows && it.col in cols }

        return neighbors.filter { (nDir, nPos) ->
            val nTile = matrix[nPos]
            nTile is PipeTile && nDir.opposite in nTile.connections
        }
    }
}
