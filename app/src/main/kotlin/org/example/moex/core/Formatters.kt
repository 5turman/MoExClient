package org.example.moex.core

/**
 * Created by 5turman on 28.03.2017.
 */
fun String.toDoubleOrDefault(value: Double): Double {
    var result = value
    try {
        result = this.toDouble()
    } catch (ignore: NumberFormatException) {
    }
    return result
}
