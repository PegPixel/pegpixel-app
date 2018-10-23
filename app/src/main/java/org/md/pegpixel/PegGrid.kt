package org.md.pegpixel

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.*
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.ui.PegView

class PegGrid {

    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

        fun initialize(columnCount: Int, rowCount: Int, defaultColor: Int, tableLayout: TableLayout): List<PegView> {
            return createPegs(columnCount, rowCount, defaultColor)
                    .flatMap{addToTable(it, tableLayout)}


        }

        private fun createPegs(columnCount: Int, rowCount: Int, defaultColor: Int): List<List<Peg>> {
            return(rowCount - 1 downTo 0).map{currentRow ->
                    (0 until columnCount).map{ currentColumn ->
                    Log.i("STUFF", "creating pegview column: $currentColumn row: $currentRow")
                        Peg(currentColumn, currentRow, false, defaultColor)
                }
            }
        }

        private fun addToTable(pegs:List<Peg>, tableLayout: TableLayout): List<PegView>{
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

            return allPegsInRow
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