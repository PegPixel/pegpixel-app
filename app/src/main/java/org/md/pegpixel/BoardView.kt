package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout


class BoardView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        //val pegGrid = PegGrid(7, 5)
        val rootTable = findViewById<TableLayout>(R.id.pegTableLayout)

        val pegGrid = PegGrid.addGridTo(columnCount = 7, rowCount = 5, tableLayout = rootTable)
    }
}


