package org.md.pegpixel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TableLayout
import android.widget.Toast
import kotlin.concurrent.thread


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
        val sendAllButton = findViewById<Button>(R.id.sendAllButton)

        val allPegs = initiateGrid(rootTable)
        initiateSendAllButton(sendAllButton, allPegs)
    }

    private fun initiateSendAllButton(sendAllButton: Button, allPegs: List<PegView>) {
        sendAllButton.setOnClickListener{
            thread{
                allPegs.forEach{pegView ->
                    val json = PegGridToJson.createJsonFor(pegView)
                    sendViaBt(json)
                    // receiving side cannot handle the speed of the transmission -> throttle
                    Thread.sleep(50)
                }
            }
        }
    }

    private val sendViaBt: (String) -> Unit = {data ->
        thread {
            bluetoothConnectionToBoard.sendData(data)
        }
    }

    private fun initiateGrid(rootTable: TableLayout): List<PegView> {
        val allPegsWithButtons = PegGrid.addGridTo(
                columnCount = 7,
                rowCount = 1,
                tableLayout = rootTable
        )

        allPegsWithButtons.forEach {pegViewWithButton ->
            pegViewWithButton.button.setOnClickListener{
                pegViewWithButton.pegView.toggleSelect()
                //val json = PegGridToJson.createJsonFor(allPegs)
                val json = PegGridToJson.createJsonFor(pegViewWithButton.pegView)
                sendViaBt(json)
            }
        }

        return allPegsWithButtons.map { it.pegView }
    }

    private fun initiateBluetoothConnection() {
        thread {
            bluetoothConnectionToBoard = BluetoothConnectionToBoard.initiate(bluetoothDeviceName) { errorMessage ->

                Looper.prepare()
                showShortToast(errorMessage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothConnectionToBoard.close()
    }
}


