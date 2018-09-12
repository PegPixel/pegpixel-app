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




class BluetoothConnectionToBoard : BroadcastReceiver() {

    private val discoveredDevices = mutableSetOf<DiscoveredDevice>()

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (BluetoothDevice.ACTION_FOUND == action) {
            // Discovery has found a device. Get the BluetoothDevice
            // object and its info from the Intent.
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            discoveredDevices.add(DiscoveredDevice(deviceName, deviceHardwareAddress))
            Log.i("STUFF", "Discovered new device: $deviceName")
        } else {
            Log.i("STUFF", "Bluetooth action: ${intent.action}")
        }
    }

    companion object {
        fun initiate(activity: Activity): BluetoothConnectionToBoard{
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if(mBluetoothAdapter.isEnabled){

                val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
                val broadcastReceiver = BluetoothConnectionToBoard()

                activity.registerReceiver(broadcastReceiver, filter)
                Log.e("STUFF", "Bluetooth listener is active")
                return broadcastReceiver
            } else{
                Log.e("STUFF", "Bluetooth is disabled - app will not function")
                return BluetoothConnectionToBoard()
            }
        }

    }
}

class DiscoveredDevice(name: String, macAddress: String)


// TODO "bonded devices abfragen"
/*
Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

if (pairedDevices.size() > 0) {
// There are paired devices. Get the name and address of each paired device.
for (BluetoothDevice device : pairedDevices) {
String deviceName = device.getName();
String deviceHardwareAddress = device.getAddress(); // MAC address
}
}
 */