package org.md.pegpixel

import android.view.View

class PegView(
        val x: Int,
        val y: Int,
        var selected: Boolean) {

    fun toggleSelect() {
        selected != selected
    }
}