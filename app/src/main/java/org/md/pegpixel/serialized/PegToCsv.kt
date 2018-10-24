package org.md.pegpixel.serialized

import android.graphics.Color
import org.md.pegpixel.pegboard.Peg
import java.util.*

class PegToCsv{
    companion object {
        fun createCsvFor(peg: Peg): String {
            val csvBuilder = StringJoiner(",", "", "\n")

            csvBuilder.add(peg.columnIndex.toString())
            csvBuilder.add(peg.rowIndex.toString())
            csvBuilder.add(peg.selected.toString())
            peg.color.let {
                csvBuilder.add(Color.red(peg.color).toString())
                csvBuilder.add(Color.green(peg.color).toString())
                csvBuilder.add(Color.blue(peg.color).toString())

            }
            return csvBuilder.toString()
        }
    }
}