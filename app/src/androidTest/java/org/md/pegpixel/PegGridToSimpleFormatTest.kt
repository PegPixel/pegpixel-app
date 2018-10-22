package org.md.pegpixel


import android.graphics.Color
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith


import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`



@RunWith(AndroidJUnit4::class)
class PegGridToSimpleFormatTest {

    @Test
    fun extracts_selected_color() {
        val peg = Peg(1, 1, true)
        val green = Color.valueOf(0f, 1f, 0f)
        peg.color = green.toArgb()
        val createdJson = PegGridToSimpleFormat.createSimpleFormatFor(peg)
        assertThat(createdJson, `is`("""11t000255000"""))
    }

    @Test
    fun extracts_when_no_color_selected() {
        val peg = Peg(1, 1, true)
        val green = Color.valueOf(0f, 0f, 0f)
        peg.color = green.toArgb()
        val createdJson = PegGridToSimpleFormat.createSimpleFormatFor(peg)
        assertThat(createdJson, `is`("""11t000000000"""))
    }
}