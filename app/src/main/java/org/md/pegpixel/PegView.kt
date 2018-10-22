package org.md.pegpixel

import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
import android.widget.CompoundButton

data class PegView(val peg: Peg, val button: CompoundButton) {
    fun selectWithColor(newColor: Int){
        peg.selected = true
        updateColor(newColor)
        button.isChecked = peg.selected
    }


    fun updateColor(newColor: Int) {
        peg.color = newColor
        val newColorStateList = ColorStateList.valueOf(newColor)
        CompoundButtonCompat.setButtonTintList(button, newColorStateList)
    }
}