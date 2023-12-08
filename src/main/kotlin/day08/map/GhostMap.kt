package day08.map

internal typealias Steps = Long
internal typealias Label = String

internal class GhostMap(
    private val instruction: List<Direction>,
    val nodes: Map<Label, Fork>,
) {
    private fun Steps.instructionIndex(): Int = (this % instruction.size).toInt()
    private fun Steps.direction(): Direction = instruction[instructionIndex()]

    private val zTravelMap: Map<Label, Array<Pin?>> = nodes.keys.filter { it.endsWith('Z') }
        .associateWith { zLabel ->
            instruction.indices
                .map { i ->
                    Pin(zLabel, i.toLong()).travel { it.isZ() }?.let { Pin(it.label, it.steps - i)}
                }.toTypedArray()
        }

    inner class Pin(
        val label: Label,
        val steps: Steps,
    ) {
        val next: Pin get() = Pin(nodes[label]!!.go(steps.direction()), steps + 1)

        fun isZ(): Boolean = label.endsWith('Z')

        fun travel(stepLimit: Steps = 100_000, checkOk: (Pin) -> Boolean): Pin? {
            var pin = this
            while (pin.steps <= steps + stepLimit) {
                pin = pin.next
                if (checkOk(pin)) return pin
            }
            return null
        }

        /** Fast travel from Z-node to the next Z-node using the [zTravelMap] */
        fun zTravel(): Pin? {
            val links = zTravelMap[label]!!
            val linked = links[steps.instructionIndex()] ?: return null
            return Pin(linked.label, steps + linked.steps)
        }

        override fun toString(): String = "$label $steps"
    }

    fun moveAllToZ(pins: List<Pin>): List<Pin> {
        val currentPins = pins.toMutableList()

        // move all pins to their first Z node
        for (i in currentPins.indices) {
            currentPins[i] = currentPins[i].travel { it.isZ() }!!
        }

        // sync up pins on Z's
        while (!currentPins.all { it.steps == currentPins[0].steps }) {
            val minPin = currentPins.indices.minBy { currentPins[it].steps }
            currentPins[minPin] = currentPins[minPin].zTravel()!!
        }
        return currentPins
    }
}