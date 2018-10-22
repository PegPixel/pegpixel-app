package org.md.pegpixel

import android.app.FragmentManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import org.md.pegpixel.bluetooth.BluetoothConnectionStatus
import org.md.pegpixel.bluetooth.BluetoothConnectionToBoardManager
import kotlin.concurrent.thread

class Board(private val allPegsWithButtons: List<PegView>, private val bluetoothDeviceName: String, bluetoothConnectionStatus: BluetoothConnectionStatus) : PickColorFragment.SelectedColorListener {

    private val bluetoothConnectionToBoard: BluetoothConnectionToBoardManager = BluetoothConnectionToBoardManager(bluetoothDeviceName, bluetoothConnectionStatus)

    companion object {
        fun create(
                rootTable: TableLayout,
                bluetoothDeviceName: String,
                bluetoothConnectionStatus: BluetoothConnectionStatus): Board {

            val allPegsWithButtons = PegGrid.initialize(
                    columnCount = 4,
                    rowCount = 4,
                    tableLayout = rootTable
            )

            return Board(allPegsWithButtons, bluetoothDeviceName, bluetoothConnectionStatus)
        }

    }

    fun initiateSendAllButton(sendAllButton: Button) {
        sendAllButton.setOnClickListener {
            thread {
                allPegsWithButtons.forEach { pegView ->
                    sendViaBt(pegView.peg)
                    // receiving side cannot handle the speed of the transmission -> throttle
                    Thread.sleep(50)
                }
            }
        }
    }


    fun setupPegEvents(fragmentManager: FragmentManager): List<Peg> {

        allPegsWithButtons.forEach { pegViewWithCheckbox ->
            pegViewWithCheckbox.updateColor(Color.RED)

            pegViewWithCheckbox.button.setOnClickListener {
                pegViewWithCheckbox.peg.toggleSelect()
                sendViaBt(pegViewWithCheckbox.peg)
            }
            pegViewWithCheckbox.button.setOnLongClickListener {
                showColorPicker(pegViewWithCheckbox, fragmentManager)
            }
        }

        return allPegsWithButtons.map { it.peg }
    }

    private fun showColorPicker(pegWithCheckbox: PegView, fragmentManager: FragmentManager): Boolean {
        val pickColorFragment = PickColorFragment()
        val bundle = Bundle()
        bundle.putInt("pegViewId", pegWithCheckbox.button.id)
        pickColorFragment.arguments = bundle
        pickColorFragment.show(fragmentManager, "PickColorDialogFragment")
        return true
    }


    private fun sendViaBt(peg: Peg) {
        val json = PegGridToJson.createJsonFor(peg)
        thread {
            bluetoothConnectionToBoard.sendData("$json\n")
        }
    }

    fun initiateBluetoothConnection() {
        bluetoothConnectionToBoard.attemptConnection(bluetoothDeviceName)
    }

    override fun handleSelectedColor(pegViewId: Int, selectedColor: Int) {
        allPegsWithButtons
                .filter { !it.button.isChecked }
                .forEach { it.updateColor(selectedColor) }

        allPegsWithButtons.find {
            it.button.id == pegViewId
        }?.let {
            it.selectWithColor(selectedColor)
            sendViaBt(it.peg)
        }

    }

    fun closeBtConnection() {
        bluetoothConnectionToBoard.close()
    }
}