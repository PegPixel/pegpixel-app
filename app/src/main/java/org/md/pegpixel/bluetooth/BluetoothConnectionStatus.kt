package org.md.pegpixel.bluetooth

import android.content.Context
import android.widget.CheckBox
import org.md.pegpixel.R

class BluetoothConnectionStatus(private val connectionStatusCheckBox: CheckBox, private val context: Context){
    fun setConnected() {
        connectionStatusCheckBox.isChecked = true
        connectionStatusCheckBox.text = context.getString(R.string.status_connected)
    }

    fun setDisconnected() {
        connectionStatusCheckBox.isChecked = false
        connectionStatusCheckBox.text = context.getString(R.string.status_not_connected)
    }

}