package org.md.pegpixel

class Peg(
        val columnIndex: Int,
        val rowIndex: Int,
        var selected: Boolean,
        var color: Int) {

    fun toggleSelect() {
        selected = !selected
    }
}