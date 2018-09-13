package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.Toast


class BoardView : AppCompatActivity() {

    private val bluetoothDeviceName = "DSD TECH HC-05"
    private var bluetoothConnectionToBoard: BluetoothConnectionToBoard =  PendingBluetoothConnectionToBoard(bluetoothDeviceName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        bluetoothConnectionToBoard = BluetoothConnectionToBoard.initiate(bluetoothDeviceName)

        val rootTable = findViewById<TableLayout>(R.id.pegTableLayout)

        PegGrid.addGridTo(columnCount = 7, rowCount = 5, tableLayout = rootTable)

        bluetoothConnectionToBoard.sendData("Board is connected") { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        bluetoothConnectionToBoard.close()
    }
}


