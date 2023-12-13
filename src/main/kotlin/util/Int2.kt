package util

import kotlin.math.abs
import kotlin.math.sqrt

internal data class Int2(
    val x: Int,
    val y: Int,
)  {
    companion object {
        val Origin = Int2(0, 0)
    }
    operator fun plus(o: Int2): Int2 = Int2(x + o.x, y + o.y)

    operator fun minus(o: Int2): Int2 = Int2(x - o.x, y - o.y)

    operator fun times(factor: Int): Int2 = Int2(x * factor, y * factor)

    infix fun manhattanTo(o: Int2): Int = abs(x - o.x) + abs(y - o.y)

    infix fun cartesianTo(o: Int2): Double =
        (this - o).let { sqrt((it.x * it.x + it.y * it.y).toDouble())}

    override fun toString(): String = "($x, $y)"
}
