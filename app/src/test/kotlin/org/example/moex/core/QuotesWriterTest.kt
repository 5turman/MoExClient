package org.example.moex.core

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.assertFailsWith

/**
 * Created by 5turman on 6/6/2017.
 */
class QuotesWriterTest {

    @get:Rule
    val folder = TemporaryFolder()

    private lateinit var file: File
    private lateinit var writer: QuotesWriter

    @Before
    fun setUp() {
        file = folder.newFile()
        writer = QuotesWriter(file)
    }

    @Test
    fun write() {
        writer.write(155.0, 1000)
        writer.write(155.2, 1001)
        writer.write(155.45, 1002)
        writer.close()

        val result = file.readText()
        assertThat(result, `is`("155.0:1000\n155.2:1001\n155.45:1002\n"))
    }

    @Test
    fun writeAfterClose() {
        writer.write(155.0, 1000)

        writer.close()

        assertFailsWith<IOException> {
            writer.write(155.2, 1001)
        }
    }

}
