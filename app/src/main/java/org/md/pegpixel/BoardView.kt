package org.md.pegpixel

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.md.pegpixel.bluetooth.BluetoothConnectionStatus
import org.md.pegpixel.bluetooth.BluetoothConnectionToBoardManager
import kotlin.concurrent.thread


class BoardView : AppCompatActivity(), PickColorFragment.SelectedColorListener {

    private val bluetoothDeviceName = "DSD TECH HC-05"

    private var bluetoothConnectionToBoard: BluetoothConnectionToBoardManager? = null

    private var boardEvents: BoardEvents? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        val bluetoothConnectionStatus = BluetoothConnectionStatus(findViewById(R.id.connectionStatus), applicationContext)
        val bluetoothConnectionToBoard = BluetoothConnectionToBoardManager(bluetoothDeviceName, bluetoothConnectionStatus)
        bluetoothConnectionToBoard.attemptConnection(bluetoothDeviceName)

        val allPegsWithButtons = PegGrid.initialize(
                columnCount = 7,
                rowCount = 5,
                defaultColor = Color.RED,
                tableLayout = findViewById(R.id.pegTableLayout)
        )
        val board = BoardEvents(allPegsWithButtons)

        board.setupEventListeners(fragmentManager, this::sendViaBt)
        board.initiateSendAllButton(findViewById(R.id.sendAllButton), this::sendViaBt)

        this.boardEvents = board
        this.bluetoothConnectionToBoard = bluetoothConnectionToBoard
    }

    override fun handleSelectedColor(pegViewId: Int, selectedColor: Int) {
        boardEvents?.handleSelectedColor(pegViewId, selectedColor, this::sendViaBt)
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothConnectionToBoard?.close()
    }


    private fun sendViaBt(peg: Peg) {
        val json = PegGridToJson.createJsonFor(peg)
        thread {
            bluetoothConnectionToBoard?.sendData("$json\n")
        }
    }
}
