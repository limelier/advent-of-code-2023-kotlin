package day07

@JvmInline
internal value class Hand(
    private val repr: String,
) {
    companion object {
        private fun Char.labelValue(joker: Boolean): Int = when(this) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> if (joker) 1 else 11
            'T' -> 10
            else -> digitToInt()
        }

        enum class Type{
            FiveOfAKind,
            FourOfAKind,
            FullHouse,
            ThreeOfAKind,
            TwoPair,
            OnePair,
            HighCard
        }

        fun comparator(joker: Boolean) = Comparator<Hand> { h1, h2 ->
            val type = h1.type(joker)
            val otherType = h2.type(joker)

            // compare type ordinal
            if (type < otherType) {
                return@Comparator 1
            }
            if (type > otherType) {
                return@Comparator -1
            }

            // compare card values
            for (i in h1.repr.indices) {
                val res = h1.repr[i].labelValue(joker)
                    .compareTo(h2.repr[i].labelValue(joker))
                if (res != 0) return@Comparator res
            }
            return@Comparator 0
        }
    }

    private fun type(joker: Boolean): Type {
        val keyedCounts = mutableMapOf<Char, Int>()
        for (c in repr) {
            keyedCounts[c] = (keyedCounts[c] ?: 0) + 1
        }

        if (joker && 'J' in keyedCounts) {
            val jCount = keyedCounts['J']!!
            if (jCount == 5) return Type.FiveOfAKind

            val maxNonJEntry = keyedCounts.entries.filter{ it.key != 'J' }.maxBy { it.value }
            maxNonJEntry.setValue(maxNonJEntry.value + jCount)
            keyedCounts['J'] = 0
        }

        val counts = keyedCounts.values.sortedDescending()

        return when(counts[0]) {
            5 -> Type.FiveOfAKind
            4 -> Type.FourOfAKind
            3 -> if (counts[1] == 2) Type.FullHouse else Type.ThreeOfAKind
            2 -> if (counts[1] == 2) Type.TwoPair else Type.OnePair
            else -> Type.HighCard
        }
    }
}