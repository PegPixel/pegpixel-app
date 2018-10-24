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

            val color = Color.valueOf(peg.color)
            csvBuilder.append(as255String(color.red()))
            csvBuilder.append(as255String(color.green()))
            csvBuilder.append(as255String(color.blue()))
            return csvBuilder.toString()
        }

        private fun as255String(floatColor: Float): String{
            val colorString = floatColor.times(255).toInt().toString()
            return colorString.padStart(3, '0')
        }
    }
}