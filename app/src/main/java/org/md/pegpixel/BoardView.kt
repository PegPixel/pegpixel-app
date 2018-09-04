package org.md.pegpixel

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import java.util.jar.Attributes


class BoardView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)


        val pegGrid = PegGrid()
        val gridParent = findViewById<LinearLayout>(R.id.gridparent)

        gridParent.addView(pegGrid.createGrid(this))

    }
}


class PegGrid {
    fun createGrid(context: Context): TableLayout {
        val tableParams = TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)

        val tableLayout = TableLayout(context)
        tableLayout.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)// assuming the parent view is a LinearLayout

        val tableRow = TableRow(context)
        tableRow.layoutParams = tableParams// TableLayout is the parent view

        val textView = TextView(context)
        textView.layoutParams = rowParams// TableRow is the parent view
        textView.text = "Erste Reihe"

        tableRow.addView(textView)

        return tableLayout
    }
}