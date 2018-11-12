package org.md.pegpixel

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableLayout
import org.md.pegpixel.bluetooth.BluetoothConnectionToBoardManager
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.persistence.BoardRepository
import org.md.pegpixel.serialized.PegGridToJson
import org.md.pegpixel.ui.BluetoothConnectionStatus
import org.md.pegpixel.ui.BoardEvents
import org.md.pegpixel.ui.PegViewInitializer
import org.md.pegpixel.ui.PegboardView
import kotlin.concurrent.thread


class BoardView : AppCompatActivity(), PickColorFragment.SelectedColorListener {

    private val bluetoothDeviceName = "pegpixel-board"

    private var bluetoothConnectionToBoard: BluetoothConnectionToBoardManager? = null

    private var pegboardView: PegboardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_view)

        val bluetoothConnectionStatus = BluetoothConnectionStatus(findViewById(R.id.connectionStatus) as CheckBox, applicationContext)
        val bluetoothConnectionToBoard = BluetoothConnectionToBoardManager(bluetoothDeviceName, bluetoothConnectionStatus)
        bluetoothConnectionToBoard.attemptConnection(bluetoothDeviceName)

        val boardPersistence = BoardRepository(applicationContext)

        val allPegs = createPegs(7, 5, Color.RED)

        val allPegViews = PegViewInitializer.addToTable(allPegs, findViewById(R.id.pegTableLayout) as TableLayout)

        val boardEvents = BoardEvents(allPegViews)

        boardEvents.setupPegEventListeners(fragmentManager, this::sendViaBt)
        boardEvents.setupSendAllButton(findViewById(R.id.sendAllButton) as Button, this::sendViaBt)

        val boardNameEditText = findViewById(R.id.boardName)
        boardEvents.setupSaveButton(
                findViewById(R.id.saveButton) as Button,
                boardNameEditText as EditText,
                boardPersistence)

        boardEvents.setupLoadButton(
                findViewById(R.id.loadButton) as Button,
                boardNameEditText,
                boardPersistence)

        this.pegboardView = PegboardView(allPegViews)
        this.bluetoothConnectionToBoard = bluetoothConnectionToBoard
    }

    private fun createPegs(columnCount: Int, rowCount: Int, defaultColor: Int): List<List<Peg>> {
        return(rowCount - 1 downTo 0).map{currentRow ->
            (0 until columnCount).map{ currentColumn ->
                Peg(currentColumn, currentRow, false, defaultColor)
            }
        }
    }

    override fun handleSelectedColor(pegViewId: Int, selectedColor: Int) {
        pegboardView?.updateColorForNonSelectedPegs(selectedColor)
        val pegView = pegboardView?.findById(pegViewId)

        pegView?.let {
            it.selectWithColor(selectedColor)
            sendViaBt(it.peg)
        }
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
