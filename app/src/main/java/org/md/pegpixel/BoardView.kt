package org.md.pegpixel

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TableLayout
import android.widget.Toast
import kotlin.concurrent.thread


class BoardView : AppCompatActivity() {

    private val showShortToast: (String) -> Unit = { errorMessage ->
        Looper.prepare()
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private val bluetoothDeviceName = "DSD TECH HC-05"

    private var bluetoothConnectionToBoard: BluetoothConnectionToBoard =  PendingBluetoothConnectionToBoard(bluetoothDeviceName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        initiateBluetoothConnection()

        val allPegs = initiateGrid(findViewById(R.id.pegTableLayout))
        initiateSendAllButton(findViewById(R.id.sendAllButton), allPegs)
    }

    private fun initiateSendAllButton(sendAllButton: Button, allPegs: List<PegView>) {
        sendAllButton.setOnClickListener{
            thread{
                allPegs.forEach{pegView ->
                    sendViaBt(pegView)
                    // receiving side cannot handle the speed of the transmission -> throttle
                    Thread.sleep(50)
                }
            }
        }
    }

    private fun initiateGrid(rootTable: TableLayout): List<PegView> {
        val allPegsWithButtons = PegGrid.addGridTo(
                columnCount = 7,
                rowCount = 5,
                tableLayout = rootTable
        )

        allPegsWithButtons.forEach {pegViewWithCheckbox ->
            pegViewWithCheckbox.updateColor(Color.RED)

            pegViewWithCheckbox.checkBox.setOnClickListener{
                pegViewWithCheckbox.pegView.toggleSelect()
                sendViaBt(pegViewWithCheckbox.pegView)
            }
            pegViewWithCheckbox.checkBox.setOnLongClickListener{
                showColorPicker(allPegsWithButtons, pegViewWithCheckbox)
            }
        }

        return allPegsWithButtons.map { it.pegView }
    }

    private fun showColorPicker(allPegsWithButtons: List<PegViewWithCheckBox>, pegViewWithCheckbox: PegViewWithCheckBox): Boolean {
        val pickColorFragment = PickColorFragment()
        pickColorFragment.handleSelectedColor = { selectedColor ->
            allPegsWithButtons
                    .filter { !it.checkBox.isChecked }
                    .forEach { it.updateColor(selectedColor) }
            pegViewWithCheckbox.selectWithColor(selectedColor)
            sendViaBt(pegViewWithCheckbox.pegView)
        }
        pickColorFragment.show(fragmentManager, "PickColorDialogFragment")
        return true
    }

    private fun sendViaBt(pegView: PegView) {
        val json = PegGridToJson.createJsonFor(pegView)
        thread {
            bluetoothConnectionToBoard.sendData("$json\n", showShortToast)
        }
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
