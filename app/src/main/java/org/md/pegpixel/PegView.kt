package org.md.pegpixel

class PegView(
        val xIndex: Int,
        val yIndex: Int,
        var selected: Boolean) {

    var color: Int? = null

    fun toggleSelect() {
        selected = !selected
    }
}