package org.example.moex.core

/**
 * Created by 5turman on 6/5/2017.
 */
class FloatRing(val size: Int) {

    private val values = FloatArray(size)

    private var start = -1
    private var count = 0

    fun add(value: Float) {
        if (start == -1) {
            start = 0
        }
        values[(start + count) % size] = value
        if (count < size) {
            ++count
        } else {
            start = (start + 1) % size
        }
    }

    fun getCount() = count

    fun get(index: Int): Float {
        return values[index % size]
    }

    fun getStart(): Int {
        return start
    }

}
