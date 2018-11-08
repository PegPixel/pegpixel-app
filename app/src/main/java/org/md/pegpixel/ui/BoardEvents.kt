package org.md.pegpixel.ui

import android.app.FragmentManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.md.pegpixel.PickColorFragment
import org.md.pegpixel.pegboard.Peg
import org.md.pegpixel.pegboard.Pegboard
import org.md.pegpixel.persistence.BoardRepository
import kotlin.concurrent.thread

class BoardEvents(private val allPegViews: List<PegView>){

    fun setupSendAllButton(sendAllButton: Button, sendViaBt: (Peg) -> Unit) {
        sendAllButton.setOnClickListener {
            thread {
                allPegViews.forEach { pegView ->
                    sendViaBt(pegView.peg)
                    // receiving side cannot handle the speed of the transmission -> throttle
                    Thread.sleep(50)
                }
            }
        }
    }


    fun setupPegEventListeners(fragmentManager: FragmentManager, sendViaBt: (Peg) -> Unit) {
        allPegViews.forEach { pegView ->
            pegView.button.setOnClickListener {
                pegView.toggleSelect()
                sendViaBt(pegView.peg)
            }
            pegView.button.setOnLongClickListener {
                showColorPicker(pegView, fragmentManager)
            }
        }
    }

    private fun showColorPicker(pegWithCheckbox: PegView, fragmentManager: FragmentManager): Boolean {
        val pickColorFragment = PickColorFragment()
        val bundle = Bundle()
        bundle.putInt("pegViewId", pegWithCheckbox.button.id)
        pickColorFragment.arguments = bundle
        pickColorFragment.show(fragmentManager, "PickColorDialogFragment")
        return true
    }

    fun setupSaveButton(saveButton: Button,
                        boardName: EditText,
                        boardRepository: BoardRepository) {
        saveButton.setOnClickListener{ _ ->
            val pegboard = Pegboard(boardName.text.toString(), allPegViews.map { it.peg })
            boardRepository.save(pegboard)
        }
    }


    fun setupLoadButton(loadButton: Button,
                        boardName: EditText,
                        boardRepository: BoardRepository) {
        loadButton.setOnClickListener{ _ ->
            val name = boardName.text.toString()
            boardRepository.load(name).thenAccept { pegboard ->
                pegboard?.pegs?.forEach { loadedPeg ->
                    val findMatching = findMatching(loadedPeg)
                    findMatching?.update(loadedPeg.selected, loadedPeg.color)
                }
                boardName.setText(pegboard?.name, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun findMatching(loadedPeg: Peg): PegView? {
        return allPegViews.find {
            it.peg.columnIndex == loadedPeg.columnIndex &&
            it.peg.rowIndex    == loadedPeg.rowIndex
        }
    }
}