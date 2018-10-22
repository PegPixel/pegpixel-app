package org.md.pegpixel.bluetooth

import android.util.Log
import android.bluetooth.BluetoothSocket
import java.io.IOException

interface BluetoothConnectionToBoard {
    fun sendData(data: String, handleConnectionError: (String) -> Unit)
    fun close()
}

class EstablishedBluetoothConnectionToBoard(private val bluetoothSocket: BluetoothSocket) : BluetoothConnectionToBoard {

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        Log.i("STUFF", "sending via bt: $data")
        try {
            writeToStream(data)
        } catch (e: IOException) {
            Log.e("STUFF", "Could not send data via BT: $data")
            handleConnectionError("Could not send data")
        }
    }

    private fun writeToStream(data: String) {
        bluetoothSocket.outputStream.write(data.toByteArray())
        bluetoothSocket.outputStream.flush()
    }

    override fun close() {
        bluetoothSocket.close()
    }
}

class PendingBluetoothConnectionToBoard(private val bluetoothDeviceName: String) : BluetoothConnectionToBoard {

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        Log.i("STUFF", "NOT sent via bt (no connection) : $data")
        handleConnectionError("Bluetooth Device '$bluetoothDeviceName' is not connected")
    }

    override fun close() {
        // no Connection, so nothing to close
    }
}
