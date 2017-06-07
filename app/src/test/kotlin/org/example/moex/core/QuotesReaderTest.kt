package org.example.moex.core

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.IOException
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by 5turman on 6/6/2017.
 */
class QuotesReaderTest {

    @get:Rule
    val folder = TemporaryFolder()

    @Test
    fun fileDoesNotExist() {
        val file = folder.newFile()
        val reader = QuotesReader(file)

        assertFalse(reader.moveToNext())

        reader.close()
    }

    @Test
    fun read() {
        val file = folder.newFile().apply { writeText("155.0:1000\n155.2:1001\n") }

        val reader = QuotesReader(file)
        assertTrue(reader.moveToNext())

        assertThat(reader.getValue(), `is`(155.0))
        assertThat(reader.getTimestamp(), `is`(1000L))

        assertTrue(reader.moveToNext())

        assertThat(reader.getValue(), `is`(155.2))
        assertThat(reader.getTimestamp(), `is`(1001L))

        assertFalse(reader.moveToNext())

        reader.close()
    }

    @Test
    fun readAfterClose() {
        val file = folder.newFile().apply { writeText("155.0:1000\n155.2:1001\n") }
        val reader = QuotesReader(file)

        reader.close()

        assertFailsWith<IOException> {
            reader.moveToNext()
        }
    }

}
