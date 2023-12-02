package day02

import kotlin.math.max

internal typealias Bag = Hand

/** Check if the given [hand] can be drawn from this bag */
internal fun Bag.canDraw(hand: Hand): Boolean =
        red >= hand.red &&
        green >= hand.green &&
        blue >= hand.blue

/** Create an expanded version of this bag that the [hand] can be drawn from */
internal fun Bag.expandedToContain(hand: Hand): Bag =
    Bag(
        max(red, hand.red),
        max(green, hand.green),
        max(blue, hand.blue)
    )
