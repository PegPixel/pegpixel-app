package org.md.pegpixel.ui

import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
import android.widget.CompoundButton
import org.md.pegpixel.pegboard.Peg

data class PegView(val peg: Peg, val button: CompoundButton) {
    fun selectWithColor(newColor: Int){
        update(true, newColor)
    }

    fun update(selected: Boolean, newColor: Int) {
        peg.selected = selected
        button.isChecked = peg.selected
        updateColor(newColor)
    }

    fun updateColor(newColor: Int) {
        peg.color = newColor
        val newColorStateList = ColorStateList.valueOf(newColor)
        CompoundButtonCompat.setButtonTintList(button, newColorStateList)
    }
}