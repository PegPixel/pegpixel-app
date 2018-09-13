package org.md.pegpixel

import android.widget.Button
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow

class PegGrid {

    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)

        fun addGridTo(columnCount: Int, rowCount: Int, tableLayout: TableLayout): List<PegViewWithButton> {
            return (1..rowCount).reversed().flatMap{
                addRowWithColumns(it, columnCount, tableLayout)
            }
        }

        private fun addRowWithColumns(currentRowIndex: Int, columnCount: Int, tableLayout: TableLayout): List<PegViewWithButton> {
            val tableRow = TableRow(tableLayout.context)
            tableRow.layoutParams = tableParams

            val allPegsInRow = (1..columnCount).map {
                val button = createCheckBox(tableLayout, currentRowIndex, it)
                val pegView = PegView(currentRowIndex, it, false)
                tableRow.addView(button)
                PegViewWithButton(pegView, button)
            }
            tableLayout.addView(tableRow)

            return allPegsInRow
        }

        private fun createCheckBox(tableLayout: TableLayout, currentRowIndex: Int, currentColumnIndex: Int): Button {
            rowParams.column = currentColumnIndex
            val button = CheckBox(tableLayout.context)
            button.layoutParams = rowParams
            button.text = "$currentRowIndex-$currentColumnIndex"
            return button
        }
    }
}



data class PegViewWithButton(val pegView: PegView, val button: Button)