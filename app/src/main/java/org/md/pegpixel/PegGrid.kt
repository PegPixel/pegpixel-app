package org.md.pegpixel

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
import android.util.Log
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow

class PegGrid {

    companion object {
        private val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT)
        private val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)

        fun addGridTo(columnCount: Int, rowCount: Int, tableLayout: TableLayout): List<PegViewWithCheckBox> {
            return createPegViews(columnCount, rowCount)
                    .flatMap{addToTable(it, tableLayout)}


        }

        private fun createPegViews(columnCount: Int, rowCount: Int): List<List<PegView>> {
            return(rowCount downTo 1).map{currentRow ->
                    (1 .. columnCount).map{currentColumn ->
                    Log.i("STUFF", "creating pegview column: $currentColumn row: $currentRow")
                    PegView(currentColumn, currentRow, false)
                }
            }
        }

        private fun addToTable(pegViews:List<PegView>, tableLayout: TableLayout): List<PegViewWithCheckBox>{
            val context = tableLayout.context
            val tableRow = TableRow(context)
            tableRow.layoutParams = tableParams

            val allPegsInRow = pegViews.map {pegView ->
                rowParams.column = pegView.columnIndex
                val checkbox = createCheckBox(context, pegView)

                tableRow.addView(checkbox)
                PegViewWithCheckBox(pegView, checkbox)
            }
            tableLayout.addView(tableRow)

            return allPegsInRow
        }

        private fun createCheckBox(context: Context?, pegView: PegView): CheckBox {
            val checkbox = CheckBox(context)
            checkbox.layoutParams = rowParams
            // add for better debugging
            //checkbox.text = "${pegView.columnIndex}-${pegView.rowIndex}"
            checkbox.id = (pegView.columnIndex * 10) + pegView.rowIndex
            return checkbox
        }
    }
}



data class PegViewWithCheckBox(val pegView: PegView, val checkBox: CheckBox) {
    fun selectWithColor(newColor: Int){
        pegView.selected = true
        updateColor(newColor)
        checkBox.isChecked = pegView.selected
    }


    fun updateColor(newColor: Int) {
        pegView.color = newColor
        val newColorStateList = ColorStateList.valueOf(newColor)
        CompoundButtonCompat.setButtonTintList(checkBox, newColorStateList)
    }
}