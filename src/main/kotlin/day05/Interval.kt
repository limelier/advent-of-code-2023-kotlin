package day05

import kotlin.math.max
import kotlin.math.min

@JvmInline
internal value class Interval(
    private val range: LongRange
) {
    init {
        assert(range.first <= range.last)
        assert(range.step == 1L)
    }
    companion object {
        fun Long.startInterval(length: Long): Interval = Interval(this.rangeUntil(this + length))
    }

    val lower get() = range.first
    val upper get() = range.last

    fun moved(delta: Long): Interval = Interval(lower + delta .. upper + delta)

    /** Get the interval of the intersection with [other], or `null` if none exists */
    fun intersection(other: Interval): Interval? {
        if (upper < other.lower || other.upper < lower) return null
        return Interval(max(lower, other.lower) .. min(upper, other.upper))
    }

    /**
     * Merge two intervals that have a non-null intersection
     *
     * @throws IllegalArgumentException if the intervals are disjoint
     */
    fun merge(other: Interval): Interval {
        if (this.intersection(other) == null) throw IllegalArgumentException("Cannot merge disjoint intervals")
        return Interval(min(lower, other.lower) .. max(upper, other.upper))
    }

    operator fun contains(number: Long): Boolean = number in range

    override fun toString(): String = "[$lower..$upper]"
}
