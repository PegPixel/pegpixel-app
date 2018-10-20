package org.md.pegpixel

import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
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
                val pegView = PegView(it, currentRowIndex, false)
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
            checkbox.text = "$currentColumnIndex-$currentRowIndex"
            checkbox.id = (currentColumnIndex * 10 ) + currentRowIndex
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