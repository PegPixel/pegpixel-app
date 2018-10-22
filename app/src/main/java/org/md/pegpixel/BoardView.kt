package org.md.pegpixel

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TableLayout
import android.widget.Toast
import kotlin.concurrent.thread


class BoardView : AppCompatActivity(), PickColorFragment.SelectedColorListener {

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

    private fun initiateSendAllButton(sendAllButton: Button, allPegs: List<Peg>) {
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

    private var allPegsWithButtons = listOf<PegWithCheckBox>();

    private fun initiateGrid(rootTable: TableLayout): List<Peg> {
         allPegsWithButtons = PegGrid.addGridTo(
                columnCount = 7,
                rowCount = 5,
                tableLayout = rootTable
        )

        allPegsWithButtons.forEach {pegViewWithCheckbox ->
            pegViewWithCheckbox.updateColor(Color.RED)

            pegViewWithCheckbox.checkBox.setOnClickListener{
                pegViewWithCheckbox.peg.toggleSelect()
                sendViaBt(pegViewWithCheckbox.peg)
            }
            pegViewWithCheckbox.checkBox.setOnLongClickListener{
                showColorPicker(pegViewWithCheckbox)
            }
        }

        return allPegsWithButtons.map { it.peg }
    }

    private fun showColorPicker(pegWithCheckbox: PegWithCheckBox): Boolean {
        val pickColorFragment = PickColorFragment()
        val bundle = Bundle()
        bundle.putInt("pegViewId", pegWithCheckbox.checkBox.id)
        pickColorFragment.arguments = bundle
        pickColorFragment.show(fragmentManager, "PickColorDialogFragment")
        return true
    }

    override fun handleSelectedColor(pegViewId: Int, selectedColor: Int) {
        allPegsWithButtons
                .filter { !it.checkBox.isChecked }
                .forEach { it.updateColor(selectedColor) }

        allPegsWithButtons.find {
            it.checkBox.id == pegViewId
        }?.let {
            it.selectWithColor(selectedColor)
            sendViaBt(it.peg)
        }

    }


    private fun sendViaBt(peg: Peg) {
        val json = PegGridToJson.createJsonFor(peg)
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
