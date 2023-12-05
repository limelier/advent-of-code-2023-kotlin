package common

/** Split the list around occurrences of the [separator] */
internal fun <T> List<T>.split(separator: T): List<List<T>> {
    val lists = mutableListOf<List<T>>()
    var remaining = this
    while (remaining.isNotEmpty()) {
        val i = remaining.indexOf(separator)
        if (i == -1) {
            lists += remaining
            break
        }
        lists += remaining.take(i)
        remaining = remaining.drop(i + 1)
    }
    return lists
}