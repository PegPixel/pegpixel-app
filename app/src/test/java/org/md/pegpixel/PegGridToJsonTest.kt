package org.md.pegpixel

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class PegGridToJsonTest {

    @Test
    fun creates_json_for_not_selected_peg() {
        val peg = PegView(1, 1, true)
        val createdJson = PegGridToJson.createJsonFor(peg)

        assertThat(createdJson, `is`("""{"x":1,"y":1,"s":"f"}"""))
    }

    @Test
    fun creates_json_for_selected_peg(){

        val peg = PegView(1, 1, true)
        val red = -0x10000
        peg.color = red
        val createdJson = PegGridToJson.createJsonFor(peg)

        assertThat(createdJson, `is`("""{"x":1,"y":1,"s":"t","r":255,"g":0,"b":"0"}"""))
    }

}