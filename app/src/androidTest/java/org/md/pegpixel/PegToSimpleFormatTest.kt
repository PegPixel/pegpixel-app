package org.md.pegpixel


import android.graphics.Color
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith


import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.serialized.PegToSimpleFormat


@RunWith(AndroidJUnit4::class)
class PegToSimpleFormatTest {

    @Test
    fun extractsSelectedColor() {
        val peg = Peg(1, 1, true, Color.RED)
        val green = Color.valueOf(0f, 1f, 0f)
        peg.color = green.toArgb()
        val createdJson = PegToSimpleFormat.createSimpleFormatFor(peg)
        assertThat(createdJson, `is`("""11t000255000"""))
    }

    @Test
    fun extractsWhenNoColorSelected() {
        val peg = Peg(1, 1, true, Color.RED)
        val green = Color.valueOf(0f, 0f, 0f)
        peg.color = green.toArgb()
        val createdJson = PegToSimpleFormat.createSimpleFormatFor(peg)
        assertThat(createdJson, `is`("""11t000000000"""))
    }
}