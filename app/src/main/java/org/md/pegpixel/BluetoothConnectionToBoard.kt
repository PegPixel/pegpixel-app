package org.md.pegpixel

import android.bluetooth.BluetoothAdapter
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.widget.Toast
import java.io.IOException
import java.util.*

interface BluetoothConnectionToBoard {
    fun sendData(data: String, handleConnectionError: (String) -> Unit)
    fun close()
    companion object {
        fun initiate(bluetoothDeviceName: String): BluetoothConnectionToBoard{
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
                EstablishedBluetoothConnectionToBoard(bluetoothDevice)
            } else {
                PendingBluetoothConnectionToBoard(bluetoothDeviceName)
            }
        }
    }
}

class EstablishedBluetoothConnectionToBoard (private val bluetoothDevice: BluetoothDevice) : BluetoothConnectionToBoard {

    private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        bluetoothSocket.use { bluetoothSocket ->
            try{
                bluetoothSocket.connect()
                writeToStream(bluetoothSocket, data)
            } catch (e: IOException) {
                handleConnectionError("Could not connect to ${bluetoothDevice.name}")
            }
        }
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
class PendingBluetoothConnectionToBoard (private val bluetoothDeviceName: String) : BluetoothConnectionToBoard {

    override fun sendData(data: String, handleConnectionError: (String) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class BluetoothConnectionSocket {

}


data class PairedBluetoothDevice(val name: String, val macAddress: String)

