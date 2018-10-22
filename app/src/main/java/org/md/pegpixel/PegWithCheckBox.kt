package org.md.pegpixel

import android.content.res.ColorStateList
import android.support.v4.widget.CompoundButtonCompat
import android.widget.CheckBox

data class PegWithCheckBox(val peg: Peg, val checkBox: CheckBox) {
    fun selectWithColor(newColor: Int){
        peg.selected = true
        updateColor(newColor)
        checkBox.isChecked = peg.selected
    }


    fun updateColor(newColor: Int) {
        peg.color = newColor
        val newColorStateList = ColorStateList.valueOf(newColor)
        CompoundButtonCompat.setButtonTintList(checkBox, newColorStateList)
    }
}