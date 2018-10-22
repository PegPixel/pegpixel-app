package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.md.pegpixel.bluetooth.BluetoothConnectionStatus


class BoardView : AppCompatActivity(), PickColorFragment.SelectedColorListener {

    private val bluetoothDeviceName = "DSD TECH HC-05"

    private var board: Board? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        val bluetoothConnectionStatus = BluetoothConnectionStatus(findViewById(R.id.connectionStatus), applicationContext)


        val board = Board.create(
                    findViewById(R.id.pegTableLayout),
                    bluetoothDeviceName,
                    bluetoothConnectionStatus
                )

        board.setupEventListeners(fragmentManager)
        board.initiateSendAllButton(findViewById(R.id.sendAllButton))
        board.initiateBluetoothConnection()

        this.board = board
    }

    override fun handleSelectedColor(pegViewId: Int, selectedColor: Int) {
        board?.handleSelectedColor(pegViewId, selectedColor)
    }

    override fun onDestroy() {
        super.onDestroy()
        board?.closeBtConnection()
    }
}
