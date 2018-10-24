package org.md.pegpixel.ui

import android.content.Context
import android.view.Gravity
import android.widget.*
import org.md.pegpixel.pegboard.Peg

class PegViewInitializer {

    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

        fun addToTable(allPegs:List<List<Peg>>, tableLayout: TableLayout): List<PegView>{
            return allPegs.flatMap { pegs ->
                val context = tableLayout.context
                val tableRow = TableRow(context)
                tableRow.layoutParams = tableParams
                tableRow.gravity = Gravity.CENTER

                val allPegsInRow = pegs.map { peg ->
                    rowParams.column = peg.columnIndex
                    val checkbox = createCheckBox(context, peg)

                    tableRow.addView(checkbox)
                    PegView(peg, checkbox)
                }
                tableLayout.addView(tableRow)
                 allPegsInRow
            }
        }

        private fun createCheckBox(context: Context?, peg: Peg): CompoundButton {
            val checkbox = CheckBox(context, null, android.R.attr.radioButtonStyle)
            checkbox.layoutParams = rowParams
            val padding = 32
            checkbox.setPadding(padding, padding, padding, padding)
            checkbox.gravity = Gravity.CENTER
            // add for better debugging
            //checkbox.text = "${peg.columnIndex}-${peg.rowIndex}"
            checkbox.id = (peg.columnIndex * 10) + peg.rowIndex
            return checkbox
        }
    }
}