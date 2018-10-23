package org.md.pegpixel.pegboard

class Peg(
        val columnIndex: Int,
        val rowIndex: Int,
        var selected: Boolean,
        var color: Int) {

    fun toggleSelect() {
        selected = !selected
    }
}