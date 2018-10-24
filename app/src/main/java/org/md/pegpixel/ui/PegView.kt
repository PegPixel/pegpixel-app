package org.md.pegpixel.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.widget.CompoundButtonCompat
import android.widget.CompoundButton
import org.md.pegpixel.pegboard.Peg

data class PegView(val peg: Peg, val button: CompoundButton) {
    fun selectWithColor(newColor: Int){
        update(true, newColor)
    }

    fun update(selected: Boolean, newColor: Int) {
        peg.selected = selected
        updateColor(newColor)
        button.isChecked = peg.selected
    }

    fun toggleSelect() {
        peg.toggleSelect()
        if(peg.selected){
            updateButtonColor(peg.color)
        } else {
            updateButtonColor(Color.BLACK)
        }
        // after we change the buttons color, we have to recheck it
        button.isChecked = !peg.selected
        button.isChecked = peg.selected
    }

    private fun updateColor(newColor: Int) {
        updatePegColor(newColor)
        updateButtonColor(newColor)
    }

    private fun updateButtonColor(newColor: Int) {
        val newColorStateList = ColorStateList.valueOf(newColor)
        CompoundButtonCompat.setButtonTintList(button, newColorStateList)
    }

    fun updatePegColor(newColor: Int) {
        peg.color = newColor
    }

}