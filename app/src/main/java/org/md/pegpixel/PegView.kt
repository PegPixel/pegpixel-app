package org.md.pegpixel

class PegView(
        private val xIndex: Int,
        private val yIndex: Int,
        private var selected: Boolean) {

    constructor(
            xIndex: Int,
            yIndex: Int) : this(xIndex, yIndex, false)

    fun toggleSelect() {
        selected != selected
    }
}