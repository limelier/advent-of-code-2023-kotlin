package util

internal enum class Cardinal { N, NE, E, SE, S, SW, W, NW;
    companion object {
        val orthogonal = listOf(N, E, S, W)
        val diagonal = listOf(NE, SE, SW, NW)
    }

    val opposite get() = when(this) {
        N -> S
        NE -> SW
        E -> W
        SE -> NW
        S -> N
        SW -> NE
        W -> E
        NW -> SE
    }
}