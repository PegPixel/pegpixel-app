package org.md.pegpixel

import android.graphics.Color
import java.util.*

class PegGridToSimpleFormat{
    companion object {
        fun createSimpleFormatFor(pegView: PegView): String {
            val csvBuilder = StringBuilder()

            val selected = if(pegView.selected) "t" else "f"


            csvBuilder.append(pegView.xIndex.toString())
            csvBuilder.append(pegView.yIndex.toString())
            csvBuilder.append(selected)

            val color = Color.valueOf(pegView.color ?: 0)
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