package org.example.moex.core

/**
 * Created by 5turman on 6/5/2017.
 */
class Ring<T>(val size: Int) {

    private val values = arrayOfNulls<Any>(size)

    private var start = -1
    private var count = 0

    fun add(value: T) {
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

    fun count() = count

    @Suppress("UNCHECKED_CAST")
    fun get(index: Int): T {
        return values[index % size] as T
    }

    fun getStart(): Int {
        return start
    }

}
