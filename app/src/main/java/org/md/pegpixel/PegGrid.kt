package org.md.pegpixel

import android.widget.Button
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow

class PegGrid(private val columnCount: Int, private val rowCount: Int) {


    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)

        fun addGridTo(columnCount: Int, rowCount: Int, tableLayout: TableLayout) {
            val allPegs = (1..rowCount).flatMap{
                addRowWithColumns(it, columnCount, tableLayout)
            }
        }

        private fun addRowWithColumns(currentRowIndex: Int, columnCount: Int, tableLayout: TableLayout): List<PegView> {
            val tableRow = TableRow(tableLayout.context)
            tableRow.layoutParams = tableParams

            val allPegsInRow = (1..columnCount).map {
                val button = createButton(tableLayout, currentRowIndex, it)
                val pegView = PegView(currentRowIndex, it)
                button.setOnClickListener(PegClickListener(pegView))
                tableRow.addView(button)
                pegView
            }
            tableLayout.addView(tableRow)

            return allPegsInRow
        }

        private fun createButton(tableLayout: TableLayout, currentRowIndex: Int, currentColumnIndex: Int): Button {
            rowParams.column = currentColumnIndex
            val button = CheckBox(tableLayout.context)
            button.layoutParams = rowParams// TableRow is the parent view
            //button.text = "$currentRowIndex-$currentColumnIndex"
            return button
        }
    }
}

