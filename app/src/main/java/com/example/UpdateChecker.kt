package com.example

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object UpdateChecker {
    private const val TAG = "UpdateChecker"
    private const val VERSION_URL = "https://raw.githubusercontent.com/CharlesBoostAI/MixFlow/main/version.json"

    data class UpdateInfo(
        val hasUpdate: Boolean,
        val versionName: String,
        val downloadUrl: String
    )

    suspend fun checkForUpdate(currentVersionCode: Int): UpdateInfo = withContext(Dispatchers.IO) {
        try {
            val url = URL(VERSION_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val jsonStr = reader.use { it.readText() }
                val root = JSONObject(jsonStr)
                
                val onlineVersionCode = root.optInt("version_code", 1)
                val onlineVersionName = root.optString("version_name", "1.0")
                val onlineUrlApk = root.optString("url_apk", "")
                
                Log.d(TAG, "Local Version: $currentVersionCode, Online Version: $onlineVersionCode")
                if (onlineVersionCode > currentVersionCode) {
                    return@withContext UpdateInfo(
                        hasUpdate = true,
                        versionName = onlineVersionName,
                        downloadUrl = onlineUrlApk
                    )
                }
            } else {
                Log.e(TAG, "Server returned response code: $responseCode")
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking for update: ${e.localizedMessage}")
        }
        return@withContext UpdateInfo(hasUpdate = false, versionName = "1.0", downloadUrl = "")
    }
}
