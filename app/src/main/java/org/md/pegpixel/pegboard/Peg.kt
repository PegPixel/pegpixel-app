package org.md.pegpixel.pegboard

data class Peg(
        val columnIndex: Int,
        val rowIndex: Int,
        var selected: Boolean,
        var color: Int) {

    fun toggleSelect() {
        selected = !selected
    }
}