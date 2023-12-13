package day12

internal typealias Springs = String

internal class SpringGroup(
    private val springs: Springs,
    private val damagedGroups: List<Int>
) {
    companion object {
        fun String.parseSprings(): SpringGroup {
            val halves = this.split(' ')
            val damagedGroups = halves[1].split(",").map { it.toInt() }
            return SpringGroup(halves[0], damagedGroups)
        }

        fun Springs.complete(): Boolean = !contains('?')

        /** Check if spring group is correct, assuming it is complete */
        fun Springs.correct(damagedGroups: List<Int>): Boolean =
            split("\\.+".toRegex())
                .filter { it.isNotEmpty() }
                .map { it.length } == damagedGroups

        /** Fill in the first corrupted entry in the springs, assuming the group is not complete */
        fun Springs.fillIn(): List<String> = listOf(
            replaceFirst('?', '.'),
            replaceFirst('?', '#')
        )
    }

    fun waysToComplete(): Int {
        val deque = ArrayDeque<String>()
        deque.add(springs)

        var ways = 0
        while (deque.isNotEmpty()) {
            val spr = deque.removeFirst()
            if (spr.complete()) {
                if (spr.correct(damagedGroups)) {
                    ways++
                }
            } else {
                deque.addAll(spr.fillIn())
            }
        }

        return ways
    }
}

