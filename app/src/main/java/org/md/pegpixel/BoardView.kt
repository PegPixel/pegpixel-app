package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.Toast
import kotlin.concurrent.thread


class BoardView : AppCompatActivity() {

    private val showShortToast: (String) -> Unit = { errorMessage ->
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private val sendViaBt: (String) -> Unit = {data ->
        thread {
            bluetoothConnectionToBoard.sendData(data)
        }
    }

    private val bluetoothDeviceName = "DSD TECH HC-05"
    private var bluetoothConnectionToBoard: BluetoothConnectionToBoard =  PendingBluetoothConnectionToBoard(bluetoothDeviceName, showShortToast)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        initiateBluetoothConnection()

        val rootTable = findViewById<TableLayout>(R.id.pegTableLayout)

        PegGrid.addGridTo(
                columnCount = 7,
                rowCount = 5,
                tableLayout = rootTable,
                sendViaBt = sendViaBt
        )
    }

    private fun initiateBluetoothConnection() {
        thread {
            bluetoothConnectionToBoard = BluetoothConnectionToBoard.initiate(bluetoothDeviceName) { errorMessage ->
                showShortToast(errorMessage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothConnectionToBoard.close()
    }
}


