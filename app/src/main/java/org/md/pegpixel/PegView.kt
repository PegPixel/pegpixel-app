package org.md.pegpixel

class PegView(
        val xIndex: Int,
        val yIndex: Int,
        var selected: Boolean) {

    fun toggleSelect() {
        selected != selected
    }
}