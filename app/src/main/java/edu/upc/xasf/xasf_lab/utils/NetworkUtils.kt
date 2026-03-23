package edu.upc.xasf.xasf_lab.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager

object NetworkUtils {

    fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    fun getWifiDetails(context: Context): String {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ssid = wifiInfo.ssid ?: "Desconegut"
        val bssid = wifiInfo.bssid ?: "Desconegut"
        val rssi = wifiInfo.rssi

        val signalLevel = WifiManager.calculateSignalLevel(rssi, 5)

        return "SSID: $ssid\nBSSID: $bssid\nSeñal: $rssi dBm (Nivel $signalLevel/4)"
    }}