package org.md.pegpixel

import android.graphics.Color
import java.util.*

class PegGridToCsv{
    companion object {
        fun createCsvFor(pegView: PegView): String {
            val csvBuilder = StringJoiner(",", "", "\n")

            csvBuilder.add(pegView.xIndex.toString())
            csvBuilder.add(pegView.yIndex.toString())
            csvBuilder.add(pegView.selected.toString())
            pegView.color?.let {
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