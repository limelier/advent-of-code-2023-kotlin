package day05

import day05.Domain.Companion.domain
import kotlin.test.Test
import kotlin.test.assertEquals


class AlmanacMapTest {
    @Test
    fun `sample light to temperature`() {
        val light = listOf(
            Interval(46L..49),
            Interval(53L..62),
            Interval(74L..87),
        ).domain()

        val map = AlmanacMap(listOf(
            AlmanacMap.Entry(45, 77, 23),  // [77, 99] -> -32
            AlmanacMap.Entry(81, 45, 19),  // [45, 63] -> +36
            AlmanacMap.Entry(68, 64, 13),  // [64, 76] -> +4
        ))

        val temperature = map.apply(light)
        val expected = listOf(
            Interval(45L..55),
            Interval(78L..80),
            Interval(82L..85),
            Interval(89L..98),
        ).domain()

        assertEquals(expected, temperature)
    }

    @Test
    fun `correctly handles all kinds of intervals`() {
        val map = AlmanacMap(listOf(
            AlmanacMap.Entry(120, 20, 5),  // [20, 24] -> +100
            AlmanacMap.Entry(230, 30, 5),  // [30, 34] -> +200
        ))

        // 1: before both sources; [10, 15] -> [10, 15]
        assertEquals(
            listOf(Interval(10L..15)),
            map.apply(Interval(10L..15))
        )

        // 2: before and in first
        assertEquals(
            listOf(Interval(10L..19), Interval(120L..123)),
            map.apply(Interval(10L..23))
        )
    }
}