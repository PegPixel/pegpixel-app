package org.md.pegpixel

import android.app.FragmentManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import kotlin.concurrent.thread

class Board(private val allPegsWithButtons: List<PegView>){

    companion object {
        fun create(rootTable: TableLayout): Board {

            val allPegsWithButtons = PegGrid.initialize(
                    columnCount = 4,
                    rowCount = 4,
                    tableLayout = rootTable
            )
            return Board(allPegsWithButtons)
        }
    }

    fun initiateSendAllButton(sendAllButton: Button, sendViaBt: (Peg) -> Unit) {
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


    fun setupEventListeners(fragmentManager: FragmentManager, sendViaBt: (Peg) -> Unit): List<Peg> {
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
}