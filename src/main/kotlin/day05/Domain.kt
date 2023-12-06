package day05

@JvmInline
internal value class Domain private constructor(
    val intervals: List<Interval>
) {
    companion object {
        fun List<Interval>.domain(): Domain {
            val intervals = this
                .sortedBy { it.lower }
                .let { sortedIntervals ->
                    val mergedIntervals = mutableListOf(sortedIntervals[0])
                    for (interval in sortedIntervals.drop(1)) {
                        val last = mergedIntervals.size - 1
                        if (mergedIntervals[last].intersection(interval) != null) {
                            mergedIntervals[last] = mergedIntervals[last].merge(interval)
                        } else {
                            mergedIntervals += interval
                        }
                    }
                    mergedIntervals
                }
            return Domain(intervals)
        }
    }

    val minimum get() = intervals[0].lower
}