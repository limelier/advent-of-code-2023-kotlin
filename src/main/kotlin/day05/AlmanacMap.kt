package day05

import day05.Domain.Companion.domain
import day05.Interval.Companion.startInterval
import kotlin.math.sign

internal class AlmanacMap(
    entries: List<Entry>
) {
    private val entries = entries.sortedBy { it.source.lower }

    internal class Entry(
        destinationStart: Long,
        sourceStart: Long,
        length: Long,
    ) {
        val source = sourceStart.startInterval(length)
        private val delta = destinationStart - sourceStart

        /** Check if the source range contains the key */
        operator fun contains(key: Long): Boolean = key in source

        /** Returns the destination of the key, assuming the source range contains it */
        fun apply(key: Long): Long =
            if (this.contains(key)) {
                key + delta
            } else {
                throw IllegalArgumentException("Cannot transform key outside of source range")
            }

        /** Returns the portion of the destination range covered by the transformation of the input, if any */
        fun apply(input: Interval): Interval? = source.intersection(input)?.moved(delta)

        override fun toString(): String = "$source -> $delta"
    }

    /**
     * Find the index of the first entry that could be applied to an interval starting with [lower], or `null` if none
     * apply
     */
    private fun firstFeasibleEntry(lower: Long): Int? {
        val idx = entries.binarySearch { entry ->
            if (lower in entry) {
                0
            } else {
                (entry.source.lower - lower).sign
            }
        }

        if (idx >= 0) {
            return idx
        }

        val insertionPoint = -idx - 1
        return if (insertionPoint in entries.indices) {
            insertionPoint
        } else {
            null
        }
    }

    fun apply(input: Long): Long {
        val entryIdx = firstFeasibleEntry(input)
        if (entryIdx != null && entries[entryIdx].contains(input)) {
            return entries[entryIdx].apply(input)
        }
        return input
    }

    /**
     * Generate all intervals created by applying the map's entries to the [interval], as well as remaining
     * sub-intervals that do not have a matching entry
     */
    fun apply(interval: Interval): List<Interval> {
        val firstEntryIdx = firstFeasibleEntry(interval.lower) ?: return listOf(interval)

        val results = mutableListOf<Interval>()
        var remaining = interval
        var entryIdx = firstEntryIdx

        while (entryIdx in entries.indices) {
            val entry = entries[entryIdx]
            // if the entry applies
            if (remaining.intersection(entry.source) != null) {
                // add part before source interval without modifying it
                if (remaining.lower < entry.source.lower) {
                    results += Interval(remaining.lower..<entry.source.lower)
                }

                results += entry.apply(remaining)!!

                if (remaining.upper <= entry.source.upper) {
                    // nothing left to handle, results are done
                    return results
                }
                // remove handled parts from remaining
                remaining = Interval(entry.source.upper + 1..remaining.upper)
            } else {
                entryIdx++
            }
        }
        // add in leftover unhandled part without modifying it
        results += remaining
        return results
    }

    fun apply(inputDomain: Domain): Domain =
        inputDomain.intervals
            .flatMap { apply(it) }
            .domain()
}