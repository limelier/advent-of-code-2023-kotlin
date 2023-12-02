package day02

internal data class Hand(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    companion object {
        /** Parse a part of a line of the puzzle input into a [Hand] for that [Game] */
        fun parse(string: String): Hand {
            val components = string
                .split(",")
                .map { it.trim() }
                .associate { component ->
                    val (number, color) = component.split(" ")
                    color to number.toInt()
                }

            return Hand(
                components.getOrDefault("red", 0),
                components.getOrDefault("green", 0),
                components.getOrDefault("blue", 0)
            )
        }
    }

    val power get() = red * green * blue
}
