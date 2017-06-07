package org.example.moex.core

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Created by 5turman on 6/5/2017.
 */
class FloatRingTest {

    val ring = FloatRing(3)

    @Test
    fun initialState() {
        assertThat(ring.size, `is`(3))
        assertThat(ring.getCount(), `is`(0))
        assertThat(ring.getStart(), `is`(-1))
    }

    @Test
    fun addOneItem() {
        ring.add(12f)

        assertThat(ring.getCount(), `is`(1))
        assertThat(ring.getStart(), `is`(0))
        assertThat(ring.get(0), `is`(12f))
    }

    @Test
    fun addItemsWithOverflow() {
        ring.add(12f)
        ring.add(24f)
        ring.add(36f)

        assertThat(ring.getCount(), `is`(3))
        assertThat(ring.getStart(), `is`(0))

        assertThat(ring.get(0), `is`(12f))
        assertThat(ring.get(1), `is`(24f))
        assertThat(ring.get(2), `is`(36f))

        ring.add(72f)

        assertThat(ring.getCount(), `is`(3))
        assertThat(ring.getStart(), `is`(1))

        assertThat(ring.get(0), `is`(72f))
        assertThat(ring.get(1), `is`(24f))
        assertThat(ring.get(2), `is`(36f))

        assertThat(ring.get(ring.getStart() + ring.getCount() - 1), `is`(72f))
    }

}
