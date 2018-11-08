package org.md.pegpixel

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import org.md.pegpixel.ui.BluetoothConnectionStatus
import org.md.pegpixel.bluetooth.BluetoothConnectionToBoardManager
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.persistence.BoardPersistence
import org.md.pegpixel.serialized.PegGridToJson
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

        val bluetoothConnectionStatus = BluetoothConnectionStatus(findViewById(R.id.connectionStatus), applicationContext)
        val bluetoothConnectionToBoard = BluetoothConnectionToBoardManager(bluetoothDeviceName, bluetoothConnectionStatus)
        bluetoothConnectionToBoard.attemptConnection(bluetoothDeviceName)

        val boardPersistence = BoardPersistence(applicationContext)

        val allPegs = createPegs(7, 5, Color.RED)

        val allPegViews = PegViewInitializer.addToTable(allPegs, findViewById(R.id.pegTableLayout))

        val boardEvents = BoardEvents(allPegViews)

        boardEvents.setupPegEventListeners(fragmentManager, this::sendViaBt)
        boardEvents.setupSendAllButton(findViewById(R.id.sendAllButton), this::sendViaBt)

        val boardNameEditText = findViewById<EditText>(R.id.boardName)
        boardEvents.setupSaveButton(
                findViewById(R.id.saveButton),
                boardNameEditText,
                boardPersistence)

        boardEvents.setupLoadButton(
                findViewById(R.id.loadButton),
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
