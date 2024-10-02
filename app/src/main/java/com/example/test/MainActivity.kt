package com.example.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var ipAddressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ipAddressTextView = findViewById(R.id.ipAddressTextView)

        getIpAddress()
    }

    private fun getIpAddress() {
        CoroutineScope(Dispatchers.IO).launch {
            val ipAddress = fetchIpAddress()
            withContext(Dispatchers.Main) {
                ipAddressTextView.text = ipAddress ?: "Error fetching IP address"
            }
        }
    }

    private fun fetchIpAddress(): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://functions.yandexcloud.net/d4e2bt6jba6cmiekqmsv")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            null
        }
    }
}
