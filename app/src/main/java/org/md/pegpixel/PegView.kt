package org.md.pegpixel

class PegView(
        val columnIndex: Int,
        val rowIndex: Int,
        var selected: Boolean) {

    var color: Int? = null

    fun toggleSelect() {
        selected = !selected
    }
}