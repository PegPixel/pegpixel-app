package org.md.pegpixel

import android.bluetooth.BluetoothAdapter
import android.util.Log
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

interface BluetoothConnectionToBoard {
    fun sendData(data: String)
    fun close()
    companion object {
        private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        fun initiate(bluetoothDeviceName: String, handleConnectionError: (String) -> Unit): BluetoothConnectionToBoard{
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices = mBluetoothAdapter.bondedDevices
            val pairedBluetoothDevices = pairedDevices.map { bluetoothDevice ->
                PairedBluetoothDevice(bluetoothDevice.name, bluetoothDevice.address)
            }

            Log.i("STUFF", "paired devices: $pairedBluetoothDevices")

            val macAddress = pairedDevices.find { it.name == bluetoothDeviceName }


            return if(macAddress != null) {
                val bluetoothDevice = macAddress.let { bluetoothDevice ->
                    mBluetoothAdapter.getRemoteDevice(bluetoothDevice.address)
                }
                try{
                    val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)
                    bluetoothSocket.connect()
                    return EstablishedBluetoothConnectionToBoard(bluetoothSocket)
                } catch (e: IOException) {
                    Log.i("STUFF", "create: could not connect to $bluetoothDeviceName - ${e.message}")
                    handleConnectionError("Could not connect to ${bluetoothDevice.name}")
                    PendingBluetoothConnectionToBoard(bluetoothDeviceName, handleConnectionError)
                }
            } else {
                Log.i("STUFF", "could not connect to $bluetoothDeviceName")
                PendingBluetoothConnectionToBoard(bluetoothDeviceName, handleConnectionError)
            }
        }
    }
}

class EstablishedBluetoothConnectionToBoard (private val bluetoothSocket: BluetoothSocket) : BluetoothConnectionToBoard {

    private val theSocket = createSocket()

    private fun createSocket(): BluetoothSocket {
        return bluetoothSocket
    }

    override fun sendData(data: String) {
        writeToStream(theSocket, data)
    }

    private fun writeToStream(bluetoothSocket: BluetoothSocket, data: String) {
        bluetoothSocket.outputStream.write(data.toByteArray())
    }

    override fun close() {
        bluetoothSocket.close()
    }
}
class PendingBluetoothConnectionToBoard (private val bluetoothDeviceName: String, private val handleConnectionError: (String) -> Unit) : BluetoothConnectionToBoard {

    override fun sendData(data: String) {
        handleConnectionError("Bluetooth Device '$bluetoothDeviceName' is not connected")
    }
    override fun close() {
        // no Connection, so nothing to close
    }
}


data class PairedBluetoothDevice(val name: String, val macAddress: String)

