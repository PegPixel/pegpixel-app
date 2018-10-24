package org.md.pegpixel.serialized

import android.graphics.Color
import org.md.pegpixel.pegboard.Peg

class PegToSimpleFormat{
    companion object {
        fun createSimpleFormatFor(peg: Peg): String {
            val csvBuilder = StringBuilder()

            val selected = if(peg.selected) "t" else "f"


            csvBuilder.append(peg.columnIndex.toString())
            csvBuilder.append(peg.rowIndex.toString())
            csvBuilder.append(selected)

            csvBuilder.append(as255String(Color.red(peg.color)))
            csvBuilder.append(as255String(Color.green(peg.color)))
            csvBuilder.append(as255String(Color.blue(peg.color)))
            return csvBuilder.toString()
        }

        private fun as255String(color: Int): String{
            val colorString = color.toString()
            return colorString.padStart(3, '0')
        }
    }
}