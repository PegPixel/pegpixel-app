package org.md.pegpixel

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test


class MatrixToLineConversionTest {

    /**
     * Rows that behave straight forward
     */
    @Test
    fun converts_first_row() {
        assertThat(MatrixToLineConverter.convert(1, 1), `is`(0))
        assertThat(MatrixToLineConverter.convert(7, 1), `is`(6))
    }


    @Test
    fun converts_third_row() {
        assertThat(MatrixToLineConverter.convert(1, 3), `is`(14))
        assertThat(MatrixToLineConverter.convert(7, 3), `is`(20))
    }

    @Test
    fun converts_fifth_row() {
        assertThat(MatrixToLineConverter.convert(1, 5), `is`(28))
        assertThat(MatrixToLineConverter.convert(7, 5), `is`(34))
    }

    /**
     * Rows run counter the matrix-index-direction
     */


    @Test
    fun converts_second_row() {
        assertThat(MatrixToLineConverter.convert(7, 2), `is`(7))
        assertThat(MatrixToLineConverter.convert(1, 2), `is`(13))
    }

    @Test
    fun converts_fourth_row() {
        assertThat(MatrixToLineConverter.convert(7, 4), `is`(21))
        assertThat(MatrixToLineConverter.convert(1, 4), `is`(27))
    }

}


class MatrixToLineConverter {
    companion object {

        val ROWS = 5
        val COLUMNS = 7


        fun convert(columnOneIndex: Int, rowOneIndex: Int): Int {

            val column = columnOneIndex - 1
            val row = rowOneIndex - 1
            val rowOffset = row * COLUMNS

            return if (row % 2 == 0) {
                rowOffset + column
            } else rowOffset + COLUMNS - column - 1
        }
    }
}