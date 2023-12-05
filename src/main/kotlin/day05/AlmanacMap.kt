package day05

internal class AlmanacMap(
    private val entries: List<Entry>
) {
    internal data class Entry(
        val destinationStart: Long,
        val sourceStart: Long,
        val rangeLength: Long,
    ) {
        /** Returns the destination of the key if it belongs to the source range, or `null` otherwise */
        fun match(key: Long): Long? =
            if (key > sourceStart && key < sourceStart + rangeLength) {
                destinationStart + (key - sourceStart)
            } else {
                null
            }
    }

    fun get(key: Long): Long {
        for (entry in entries) {
            entry.match(key)?.let { return it } ?: continue
        }
        return key
    }
}