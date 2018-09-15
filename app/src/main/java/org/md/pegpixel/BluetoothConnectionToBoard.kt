package org.md.pegpixel

import android.bluetooth.BluetoothAdapter
import android.util.Log
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

interface BluetoothConnectionToBoard {
    fun sendData(data: String, handleConnectionError: (String) -> Unit)
    fun close()
    companion object {
        private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        fun initiate(bluetoothDeviceName: String, handleConnectionError: (String) -> Unit): BluetoothConnectionToBoard{
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

            val bluetoothConnectionToBoard = mBluetoothAdapter?.bondedDevices?.find {
                it.name == bluetoothDeviceName
            }?.let { bluetoothDevice ->
                val remoteBluetoothDevice = mBluetoothAdapter.getRemoteDevice(bluetoothDevice.address)
                try{
                    val bluetoothSocket = remoteBluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)
                    bluetoothSocket.connect()
                    return EstablishedBluetoothConnectionToBoard(bluetoothSocket)
                } catch (e: IOException) {
                    Log.i("STUFF", "create: could not connect to $bluetoothDeviceName - ${e.message}")
                    handleConnectionError("Could not connect to ${bluetoothDevice.name}")
                    PendingBluetoothConnectionToBoard(bluetoothDeviceName)
                }
            }
            return bluetoothConnectionToBoard?: PendingBluetoothConnectionToBoard(bluetoothDeviceName)
        }
    }
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


data class PairedBluetoothDevice(val name: String, val macAddress: String)

