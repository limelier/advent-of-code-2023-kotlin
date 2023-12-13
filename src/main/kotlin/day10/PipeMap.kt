package day10

import day10.tile.EmptyTile
import day10.tile.PipeTile
import day10.tile.StartTile
import day10.tile.Tile
import util.Direction
import util.Grid

internal class PipeMap(
    val grid: Grid<Tile>
) {
    companion object {
        class Bitmap(
            val area: Int,
            val map: Grid<Boolean>
        )
    }

    private val startPos: Grid.Pos = let {
        for (r in grid.rowIndices) {
            for (c in grid.colIndices) {
                if (grid[r, c] is StartTile) {
                    // replace start tile with the right pipe tile
                    val pos = Grid.Pos(r, c)
                    val conns = pos.connections().keys
                    grid[r, c] = PipeTile.entries.first { it.connections.containsAll(conns) }

                    return@let pos
                }
            }
        }
        throw IllegalStateException("Cannot have a pipe map with no start position")
    }

    val mainLoop: Bitmap by lazy {
        val visited = Grid.like(grid) { false }
        val unvisited = ArrayDeque<Grid.Pos>()

        unvisited.add(startPos)

        var tilesVisited = 0
        while (unvisited.isNotEmpty()) {
            val pos = unvisited.removeFirst()
            for (nPos in pos.connections().values) {
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
        val tilesInside = Grid.like(grid) { false }
        var area = 0

        for (r in grid.rowIndices) {
            var inside = false
            var segmentEnteredFrom: Direction? = null
            for (c in grid.colIndices) {
                val tile = grid[r, c]
                val onMainLoop = mainLoop.map[r, c]

                if (onMainLoop) {
                    when (tile as PipeTile) {
                        PipeTile.NorthSouth -> inside = !inside
                        PipeTile.NorthEast -> segmentEnteredFrom = Direction.N
                        PipeTile.SouthEast -> segmentEnteredFrom = Direction.S
                        PipeTile.NorthWest -> {
                            if (segmentEnteredFrom == Direction.S) inside = !inside
                            segmentEnteredFrom = null
                        }
                        PipeTile.SouthWest -> {
                            if (segmentEnteredFrom == Direction.N) inside = !inside
                            segmentEnteredFrom = null
                        }
                        PipeTile.EastWest -> assert(segmentEnteredFrom != null)
                    }

                    continue
                }

                if (inside) {
                    area++
                    tilesInside[r, c] = true
                }
            }
        }

        Bitmap(area, tilesInside)
    }

    private fun Grid.Pos.connections(): Map<Direction, Grid.Pos> {
        val connections = when(val tile = grid[this]) {
            is EmptyTile -> listOf()
            is PipeTile -> tile.connections
            is StartTile -> Direction.orthogonal
        }
        val neighbors = connections
            .associateWith { adjacent(it) }
            .filterValues { it in grid }

        return neighbors.filter { (nDir, nPos) ->
            val nTile = grid[nPos]
            nTile is PipeTile && nDir.opposite in nTile.connections
        }
    }
}
