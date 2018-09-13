package org.md.pegpixel

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import java.nio.file.Files.size
import java.util.*


class BluetoothConnectionToBoard (private val bluetoothDevice: BluetoothDevice) {

    private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun sendData(data: String) {
        val bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)
        bluetoothSocket.connect()

        val outputStream = bluetoothSocket.outputStream

        outputStream.write(data.toByteArray())

        Log.i("STUFF", "Bluetooth socket to device is etablished")
    }

    companion object {
        fun initiate(bluetoothDeviceName: String): BluetoothConnectionToBoard?{
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices = mBluetoothAdapter.bondedDevices
            val pairedBluetoothDevices = pairedDevices.map {
                PairedBluetoothDevice(it.name, it.address)
            }

            Log.i("STUFF", "paired devices: $pairedBluetoothDevices")

            val macAdress = pairedDevices.find { it.name == bluetoothDeviceName }

            return macAdress?.let {
                val bluetoothDevice = mBluetoothAdapter.getRemoteDevice(it.address)
                BluetoothConnectionToBoard(bluetoothDevice)
            }
        }
    }
}

data class PairedBluetoothDevice(val name: String, val macAddress: String)

