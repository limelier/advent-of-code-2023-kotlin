package day05

import day05.Domain.Companion.domain
import kotlin.test.Test
import kotlin.test.assertEquals

class DomainTest {
    @Test
    fun `should sort intervals`() {
        val domain = listOf(
            Interval(7L..10L),
            Interval(1L..5L),
        ).domain()
        
        assertEquals(1L, domain.intervals[0].lower)
    }
    
    @Test
    fun `should merge intervals`() {
        val domain = listOf(
            Interval(1L..7L),
            Interval(5L..10L),
        ).domain()
        
        assertEquals(Interval(1L..10L), domain.intervals[0])
        assertEquals(1, domain.intervals.size)
    }
    
    @Test
    fun `minimum works`() {
        val domain = listOf(
            Interval(7L..10L),
            Interval(1L..4L),
            Interval(3L..5L)
        ).domain()
        
        assertEquals(1L, domain.minimum)
    }
}