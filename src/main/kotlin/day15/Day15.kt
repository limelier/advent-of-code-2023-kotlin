package day15

import common.InputReader

private fun String.hash(): Int {
    var hash = 0
    for (c in this) {
        hash += c.code
        hash *= 17
        hash %= 256
    }
    return hash
}

private data class Lens(val label: String, val focalLength: Int)
private sealed interface Step { val label: String }
private data class RemoveStep(override val label: String): Step
private data class UpsertStep(override val label: String, val focalLength: Int): Step

internal fun main() {
    val instructions = InputReader("day15/input.txt")
        .lines().first()
        .split(",")

    println("Part 1: ${instructions.sumOf { it.hash() }}")

    val steps: List<Step> = instructions.map { instruction ->
        val iOp = instruction.indexOfFirst { it in "-=" }
        val label = instruction.take(iOp)
        when(instruction[iOp]) {
            '-' -> RemoveStep(label)
            '=' -> UpsertStep(label, instruction.drop(iOp + 1).toInt())
            else -> throw IllegalArgumentException("Input has invalid step $instruction")
        }
    }

    val boxes = List(256) { mutableListOf<Lens>() }
    for (step in steps) {
        val hash = step.label.hash()
        val box = boxes[hash]
        when(step) {
            is RemoveStep -> box.removeIf { it.label == step.label }
            is UpsertStep -> {
                val newLens = Lens(step.label, step.focalLength)
                val i = box.indexOfFirst { it.label == newLens.label }
                if (i != -1) {
                    box[i] = newLens
                } else {
                    box.add(newLens)
                }
            }
        }
    }

    val focusingPower = boxes.mapIndexed { i, box ->
        (i + 1) * box.mapIndexed { j, lens ->
            (j + 1) * lens.focalLength
        }.sum()
    }.sum()
    println("Part 2: $focusingPower")
}