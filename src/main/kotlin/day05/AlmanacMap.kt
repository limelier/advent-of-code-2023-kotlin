package day05

import kotlin.math.sign

internal class AlmanacMap(
    entries: List<Entry>
) {
    private val entries = entries.sortedBy { it.sourceStart }

    internal data class Entry(
        val destinationStart: Long,
        val sourceStart: Long,
        val rangeLength: Long,
    ) {
        private val sourceRange = sourceStart ..< sourceStart + rangeLength

        /** Check if the source range contains the key */
        fun contains(key: Long): Boolean = key in sourceRange

        /** Returns the destination of the key, assuming the source range contains it */
        fun transform(key: Long): Long =
            if (this.contains(key)) {
                destinationStart + (key - sourceStart)
            } else {
                throw IllegalArgumentException("Cannot transform key outside of source range")
            }
    }

    fun get(key: Long): Long {
        // search for the entry that would contain the key; negative idx means no matches were found
        val idx = entries.binarySearch { entry ->
            if (entry.contains(key)) {
                0
            } else {
                (entry.sourceStart - key).sign
            }
        }

        if (idx >= 0) {
            return entries[idx].transform(key)
        }
        return key
    }
}