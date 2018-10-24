package org.md.pegpixel

import android.graphics.Color
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.serialized.PegGridToJson

@RunWith(AndroidJUnit4::class)
class PegToJsonTest {

    @Test
    fun createsJsonForPeg() {
        val peg = Peg(1, 1, false, Color.RED)
        val createdJson = PegGridToJson.createJsonFor(peg)

        assertThat(createdJson, `is`("""{"b":0,"g":0,"r":255,"s":"f","x":1,"y":1}"""))
    }

}