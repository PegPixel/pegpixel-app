package org.md.pegpixel.ui

import android.app.FragmentManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.md.pegpixel.PickColorFragment
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.pegboard.Pegboard
import org.md.pegpixel.persistence.BoardPersistence
import kotlin.concurrent.thread

class BoardEvents(private val allPegsWithButtons: List<PegView>){

    fun setupSendAllButton(sendAllButton: Button, sendViaBt: (Peg) -> Unit) {
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


    fun setupPegEventListeners(fragmentManager: FragmentManager, sendViaBt: (Peg) -> Unit): List<Peg> {
        allPegsWithButtons.forEach { pegViewWithCheckbox ->
            pegViewWithCheckbox.updateColor(pegViewWithCheckbox.peg.color)

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

    fun handleSelectedColor(pegViewId: Int, selectedColor: Int, sendViaBt: (Peg) -> Unit) {
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

    fun setupSaveButton(saveButton: Button,
                        boardName: EditText,
                        boardPersistence: BoardPersistence) {
        saveButton.setOnClickListener{ _ ->
            val pegboard = Pegboard(boardName.text.toString(), allPegsWithButtons.map { it.peg })
            boardPersistence.save(pegboard)
        }
    }


    fun setupLoadButton(loadButton: Button,
                        boardName: EditText,
                        boardPersistence: BoardPersistence) {
        loadButton.setOnClickListener{ _ ->
            val name = boardName.text.toString()
            boardPersistence.load(name).thenAccept { pegboard ->
                pegboard?.pegs?.forEach { loadedPeg ->
                    val findMatching = findMatching(loadedPeg)
                    findMatching?.update(loadedPeg.selected, loadedPeg.color)
                }
                boardName.setText(pegboard?.name, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun findMatching(loadedPeg: Peg): PegView? {
        return allPegsWithButtons.find {
            it.peg.columnIndex == loadedPeg.columnIndex &&
            it.peg.rowIndex    == loadedPeg.rowIndex
        }
    }
}