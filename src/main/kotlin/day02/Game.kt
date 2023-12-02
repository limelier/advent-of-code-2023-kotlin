package day02

internal class Game(
    val id: Int,
    private val hands: List<Hand>
) {
    companion object {
        /** Parse a line of the day's input into a [Game] */
        fun parse(line: String): Game {
            val (header, body) = line.split(": ")

            val id = header.split(" ")[1].toInt()
            val hands = body.split(";").map(Hand::parse)

            return Game(id, hands)
        }

    }

    /** Check if every hand can be drawn from the given [bag] */
    fun isPossibleWith(bag: Bag): Boolean = hands.all { bag.canDraw(it) }

    /** Find the minimal [Bag] that this game can be played with */
    fun minimumBag(): Bag {
        var bag = Bag(0, 0, 0)
        hands.forEach { bag = bag.expandedToContain(it) }
        return bag
    }
}