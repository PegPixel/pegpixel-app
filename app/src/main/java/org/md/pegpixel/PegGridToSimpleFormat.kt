package org.md.pegpixel

import android.graphics.Color

class PegGridToSimpleFormat{
    companion object {
        fun createSimpleFormatFor(peg: Peg): String {
            val csvBuilder = StringBuilder()

            val selected = if(peg.selected) "t" else "f"


            csvBuilder.append(peg.columnIndex.toString())
            csvBuilder.append(peg.rowIndex.toString())
            csvBuilder.append(selected)

            val color = Color.valueOf(peg.color ?: 0)
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