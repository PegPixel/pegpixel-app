package org.md.pegpixel

import android.view.View

class PegClickListener(private  val pegView: PegView) : View.OnClickListener {
    override fun onClick(v: View?) {
        pegView.toggleSelect()
    }

}
