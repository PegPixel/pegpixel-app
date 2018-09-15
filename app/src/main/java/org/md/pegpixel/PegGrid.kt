package org.md.pegpixel

import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow

class PegGrid {

    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)

        fun addGridTo(columnCount: Int, rowCount: Int, tableLayout: TableLayout): List<PegViewWithCheckBox> {
            return (1..rowCount).reversed().flatMap{
                addRowWithColumns(it, columnCount, tableLayout)
            }
        }

        private fun addRowWithColumns(currentRowIndex: Int, columnCount: Int, tableLayout: TableLayout): List<PegViewWithCheckBox> {
            val tableRow = TableRow(tableLayout.context)
            tableRow.layoutParams = tableParams

            val allPegsInRow = (1..columnCount).map {
                val checkbox = createCheckbox(tableLayout, currentRowIndex, it)
                val pegView = PegView(currentRowIndex, it, false)
                tableRow.addView(checkbox)
                PegViewWithCheckBox(pegView, checkbox)
            }
            tableLayout.addView(tableRow)

            return allPegsInRow
        }

        private fun createCheckbox(tableLayout: TableLayout, currentRowIndex: Int, currentColumnIndex: Int): CheckBox {
            rowParams.column = currentColumnIndex
            val checkbox = CheckBox(tableLayout.context)
            checkbox.layoutParams = rowParams
            checkbox.text = "$currentRowIndex-$currentColumnIndex"
            return checkbox
        }
    }
}



data class PegViewWithCheckBox(val pegView: PegView, val checkBox: CheckBox)