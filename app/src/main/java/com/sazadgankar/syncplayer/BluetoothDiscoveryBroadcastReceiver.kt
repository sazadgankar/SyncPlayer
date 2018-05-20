package com.sazadgankar.syncplayer

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BluetoothDiscoveryBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (BluetoothDevice.ACTION_FOUND == action) {
            // Device Found, add to list
        }
    }
}