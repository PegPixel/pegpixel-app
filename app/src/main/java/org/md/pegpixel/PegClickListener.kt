package org.md.pegpixel

import android.view.View

class PegClickListener(
        private val pegView: PegView,
        private val pegGrid: PegGrid,
        private val sendViaBt: (String) -> Unit) : View.OnClickListener {
    override fun onClick(v: View?) {
        pegView.toggleSelect()
        val json = pegGrid.createJson()
        sendViaBt(json)
    }

}
