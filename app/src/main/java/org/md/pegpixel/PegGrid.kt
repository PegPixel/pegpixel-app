package org.md.pegpixel

import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import org.json.JSONObject

class PegGrid(private val columnCount: Int, private val rowCount: Int, private val allPegs: List<PegView>) {
    fun createJson() {
        Log.i("STUFF","Created JSON: ")
        PegGridToJson.createJsonFor(allPegs)
    }


    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)

        fun addGridTo(columnCount: Int, rowCount: Int, tableLayout: TableLayout) {
            val allPegs = (1..rowCount).reversed().flatMap{
                addRowWithColumns(it, columnCount, tableLayout)
            }

            val pegGrid = PegGrid(columnCount, rowCount, allPegs.map{it.first})

            allPegs.forEach{it.second.setOnClickListener(PegClickListener(it.first, pegGrid))}
        }

        private fun addRowWithColumns(currentRowIndex: Int, columnCount: Int, tableLayout: TableLayout): List<Pair<PegView, Button>> {
            val tableRow = TableRow(tableLayout.context)
            tableRow.layoutParams = tableParams

            val allPegsInRow = (1..columnCount).map {
                val button = createCheckBox(tableLayout, currentRowIndex, it)
                val pegView = PegView(currentRowIndex, it, false)
                tableRow.addView(button)
                Pair(pegView, button)
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

