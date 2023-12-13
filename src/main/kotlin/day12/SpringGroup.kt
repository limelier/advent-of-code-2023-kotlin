package day12

internal typealias Springs = String

internal class SpringGroup(
    springs: Springs,
    private val damagedGroups: List<Int>
) {
    private val springs = springs.trimStart('.')

    companion object {
        val waysToCompleteCache = mutableMapOf<String, Long>()

        val potentialDamageSpringPrefixRegex = "^([?#]+).*$".toRegex()

        fun parseSprings(text: String, numbers: String): SpringGroup {
            val damagedGroups = numbers.split(",").map { it.toInt() }
            return SpringGroup(text, damagedGroups)
        }
    }

    fun waysToComplete(): Long = waysToCompleteCache.getOrPut(this.toString()) { waysToCompleteCacheMiss() }

    private fun waysToCompleteCacheMiss(): Long {
        if (damagedGroups.isEmpty()) {
            return if (springs.isEmpty() || springs.none { it == '#' }) 1 else 0
        }
        if (springs.length < damagedGroups.size - 1 + damagedGroups.sum()) return 0

        if (springs.startsWith('#')) {
            val prefix = springs.take(damagedGroups[0])
            if ('.' in prefix) return 0
            if (prefix.length >= springs.length) return 1
            if (springs[prefix.length] == '#') return 0

            return SpringGroup(springs.drop(prefix.length + 1), damagedGroups.drop(1)).waysToComplete()
        }

        // springs starts with '?'

        val potentialDamageGroup = potentialDamageSpringPrefixRegex.matchEntire(springs)!!.groupValues[1].length

        var sum = SpringGroup(springs.drop(1), damagedGroups).waysToComplete()  // replace ? with .
        if (potentialDamageGroup >= damagedGroups[0]) {
            sum += SpringGroup("#".repeat(damagedGroups[0]) + springs.drop(damagedGroups[0]), damagedGroups)
                .waysToComplete()
        }
        return sum
    }

    override fun toString(): String = "$springs ${damagedGroups.joinToString(",")}"
}

