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

        initiateGrid(rootTable)
    }

    private fun initiateGrid(rootTable: TableLayout) {
        val columnCount = 7
        val rowCount = 5

        val allPegsWithButtons = PegGrid.addGridTo(
                columnCount = columnCount,
                rowCount = rowCount,
                tableLayout = rootTable
        )

        val allPegs = allPegsWithButtons.map { it.pegView }

        allPegsWithButtons.forEach {pegViewWithButton ->
            pegViewWithButton.button.setOnClickListener{
                pegViewWithButton.pegView.toggleSelect()
                val json = PegGridToJson.createJsonFor(allPegs)
                sendViaBt(json)
            }
        }


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


