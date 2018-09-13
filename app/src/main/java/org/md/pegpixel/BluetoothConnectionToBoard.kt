package org.md.pegpixel

import android.bluetooth.BluetoothAdapter
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

interface BluetoothConnectionToBoard {
    fun sendData(data: String)
    fun close()
    companion object {
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
                EstablishedBluetoothConnectionToBoard.create(bluetoothDevice, handleConnectionError)
            } else {
                Log.i("STUFF", "could not connect to $bluetoothDeviceName")
                PendingBluetoothConnectionToBoard(bluetoothDeviceName, handleConnectionError)
            }
        }
    }
}

class EstablishedBluetoothConnectionToBoard (private val bluetoothSocket: BluetoothSocket) : BluetoothConnectionToBoard {

    companion object {
        private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        fun create(bluetoothDevice: BluetoothDevice, handleConnectionError: (String) -> Unit): BluetoothConnectionToBoard{
            val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)
            return try{
                bluetoothSocket.connect()
                EstablishedBluetoothConnectionToBoard(bluetoothSocket)
            } catch (e: IOException) {
                Log.i("STUFF", "create: could not connect to $bluetoothSocket - ${e.message}")
                handleConnectionError("Could not connect to ${bluetoothDevice.name}")
                PendingBluetoothConnectionToBoard(bluetoothDevice.name, handleConnectionError)
            }
        }
    }

    override fun sendData(data: String) {
        writeToStream(bluetoothSocket, data)
    }

    private fun writeToStream(bluetoothSocket: BluetoothSocket, data: String) {
        bluetoothSocket.outputStream.use { outputStream ->
            outputStream.write(data.toByteArray())
        }
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
        // Do nothing, because we don't have a connection anyway
    }
}

class BluetoothConnectionSocket {

}


data class PairedBluetoothDevice(val name: String, val macAddress: String)

