package com.example.syscourier.activities

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.syscourier.MiApp
import com.example.syscourier.R
import okhttp3.OkHttpClient
import okhttp3.Request

class AsignacionesActivityInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asignaciones_activity)

        val id = "1"  // Puedes reemplazar esto con el valor que desees

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiainfo/$id")
                runOnUiThread {
                    // Manejar la respuesta aquí
                    Toast.makeText(this@AsignacionesActivityInfo, result, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun makeGetRequest(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() ?: "Error en la solicitud"
    }
}
