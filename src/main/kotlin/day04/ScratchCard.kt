package day04

internal data class ScratchCard(
    val id: Int,
    val winningNumbers: Set<Int>,
    val chosenNumbers: Set<Int>
) {
    val wins by lazy {
        winningNumbers.intersect(chosenNumbers).size
    }
    val points get() = when (wins) {
        0 -> 0
        1 -> 1
        else -> 1L.shl(wins - 1)
    }
}