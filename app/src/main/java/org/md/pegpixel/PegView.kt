package org.md.pegpixel

import android.view.View

class PegView(
        val xIndex: Int,
        val yIndex: Int,
        var selected: Boolean) {

    fun toggleSelect() {
        selected != selected
    }
}