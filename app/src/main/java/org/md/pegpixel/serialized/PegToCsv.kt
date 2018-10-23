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
            peg.color?.let {
                val color = Color.valueOf(it)
                csvBuilder.add(as255String(color.red()))
                csvBuilder.add(as255String(color.green()))
                csvBuilder.add(as255String(color.blue()))

            }
            return csvBuilder.toString()
        }

        private fun as255String(floatColor: Float): String{
            return floatColor.times(255).toInt().toString()
        }
    }
}