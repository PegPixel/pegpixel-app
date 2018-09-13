package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.Toast


class BoardView : AppCompatActivity() {

    private val showShortToast: (String) -> Unit = { errorMessage ->
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private val bluetoothDeviceName = "DSD TECH HC-05"
    private var bluetoothConnectionToBoard: BluetoothConnectionToBoard =  PendingBluetoothConnectionToBoard(bluetoothDeviceName, showShortToast)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        initiateBluetoothConnection()

        val rootTable = findViewById<TableLayout>(R.id.pegTableLayout)

        PegGrid.addGridTo(columnCount = 7, rowCount = 5, tableLayout = rootTable)

        setupInitBtConnectionButton()
        setupCloseBtConnectionButton()
    }

    private fun initiateBluetoothConnection() {
        bluetoothConnectionToBoard = BluetoothConnectionToBoard.initiate(bluetoothDeviceName) { errorMessage ->
            showShortToast(errorMessage)
        }
        bluetoothConnectionToBoard.sendData("Board is connected")
    }

    private fun setupInitBtConnectionButton() {
        val closeConnectionButton = findViewById<Button>(R.id.initConnectionButton)

        closeConnectionButton.setOnClickListener{
            showShortToast("Initiating BT-Connection")
            initiateBluetoothConnection()
        }
    }

    private fun setupCloseBtConnectionButton() {
        val closeConnectionButton = findViewById<Button>(R.id.closeConnectionButton)

        closeConnectionButton.setOnClickListener{
            bluetoothConnectionToBoard.close()
            showShortToast("Connection closed")
        }
    }

    override fun onStop() {
        super.onStop()
        bluetoothConnectionToBoard.close()
    }
}


