package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout


class BoardView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        //val pegGrid = PegGrid(7, 5)
        val rootTable = findViewById<TableLayout>(R.id.pegTableLayout)

        PegGrid.addGridTo(columnCount = 7, rowCount = 5, tableLayout = rootTable)

        val bluetoothConnectionToBoard = BluetoothConnectionToBoard.initiate("DSD TECH HC-05")

        if(bluetoothConnectionToBoard == null) {
            Log.e("STUFF", "Could not connect to bluetooth device")
        }
        bluetoothConnectionToBoard?.sendData("Hello World")
    }
}


