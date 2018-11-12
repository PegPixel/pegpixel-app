package org.md.pegpixel.bluetooth

import android.bluetooth.BluetoothAdapter
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.md.pegpixel.ui.BluetoothConnectionStatus
import java.io.IOException
import java.util.*

class BluetoothConnectionToBoardManager(bluetoothDeviceName: String, private val bluetoothConnectionStatus: BluetoothConnectionStatus) {
    private val theOneAndOnlyUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    private var bluetoothConnectionToBoard: BluetoothConnectionToBoard = PendingBluetoothConnectionToBoard(bluetoothDeviceName)

    fun attemptConnection(bluetoothDeviceName: String) {
        GlobalScope.launch(Dispatchers.Main) {
                val newBluetoothConnectionToBoard = async(Dispatchers.IO) {
                    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    mBluetoothAdapter?.bondedDevices?.find {
                        it.name == bluetoothDeviceName
                    }?.let { bluetoothDevice ->
                        val remoteBluetoothDevice = mBluetoothAdapter.getRemoteDevice(bluetoothDevice.address)
                        try {
                            val bluetoothSocket = remoteBluetoothDevice.createRfcommSocketToServiceRecord(theOneAndOnlyUuid)
                            bluetoothSocket.connect()
                            EstablishedBluetoothConnectionToBoard(bluetoothSocket)
                        } catch (e: IOException) {
                            Log.i("STUFF", "createFrom: could not connect to $bluetoothDeviceName - ${e.message}")
                            PendingBluetoothConnectionToBoard(bluetoothDeviceName)
                        }
                    }
                }.await()

                if (newBluetoothConnectionToBoard != null) {
                    bluetoothConnectionToBoard = newBluetoothConnectionToBoard
                    bluetoothConnectionStatus.setConnected()
                } else {
                    bluetoothConnectionToBoard = PendingBluetoothConnectionToBoard(bluetoothDeviceName)
                    bluetoothConnectionStatus.setDisconnected()
                }
            }
        }

        fun close() {
        bluetoothConnectionToBoard.close()
    }

    fun sendData(s: String) {
        bluetoothConnectionToBoard.sendData(s) { _ -> }
    }

}