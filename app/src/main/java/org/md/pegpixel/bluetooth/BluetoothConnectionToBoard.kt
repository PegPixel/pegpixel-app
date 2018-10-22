package org.md.pegpixel.bluetooth

import android.bluetooth.BluetoothAdapter
import android.util.Log
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

interface BluetoothConnectionToBoard {
    fun sendData(data: String, handleConnectionError: (String) -> Unit)
    fun close()
}

class EstablishedBluetoothConnectionToBoard (private val bluetoothSocket: BluetoothSocket) : BluetoothConnectionToBoard {

    private val theSocket = createSocket()

    private fun createSocket(): BluetoothSocket {
        return bluetoothSocket
    }

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        try{
            writeToStream(theSocket, data)
        } catch (e: IOException) {
            Log.e("STUFF", "Could not send data via BT: $data")
            handleConnectionError("Could not send data")
        }
    }

    private fun writeToStream(bluetoothSocket: BluetoothSocket, data: String) {
        bluetoothSocket.outputStream.write(data.toByteArray())
        bluetoothSocket.outputStream.flush()
    }

    override fun close() {
        bluetoothSocket.close()
    }
}
class PendingBluetoothConnectionToBoard (private val bluetoothDeviceName: String) : BluetoothConnectionToBoard {

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        handleConnectionError("Bluetooth Device '$bluetoothDeviceName' is not connected")
    }
    override fun close() {
        // no Connection, so nothing to close
    }
}
