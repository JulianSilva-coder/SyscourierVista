package com.example.syscourier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.syscourier.API.ApiClient
import com.example.syscourier.API.ApiService
import com.example.syscourier.activities.Menudesplegable
import com.example.syscourier.dto.TokenDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val loginButton: Button = findViewById(R.id.buttonlogin)
        loginButton.setOnClickListener {
            val usuarioEditText: TextView = findViewById<EditText>(R.id.editTextText)
            val contrasenaEditText: TextView = findViewById<EditText>(R.id.editTextTextPassword)

            val usuario = usuarioEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()

            val service = ApiClient.retrofit.create(ApiService::class.java)
            val call = service.obtenerDatos(usuario, contrasena)

            call.enqueue(object : Callback<TokenDTO> {
                override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                    val body = response.body()
                    if (response.code() == 202) {
                        if (body != null) {
                            // Accede a los atributos de TokenDTO
                            MiApp.accessToken = body.token
                        }
                        val intent = Intent(this@MainActivity, Menudesplegable::class.java)
                        startActivity(intent)
                    } else {
                        usuarioEditText.text = ""
                        contrasenaEditText.text = ""
                        Toast.makeText(
                            this@MainActivity,
                            "Error de inicio de sesión, credenciales inválidas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                    runOnUiThread {
                        val errorMessage: String = when (t) {
                            is ConnectException -> "No se pudo establecer la conexión al servidor."
                            is SocketTimeoutException -> "Tiempo de espera agotado. Verifica tu conexión a Internet."
                            else -> "Error de red: ${t.message}"
                        }

                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Error de conexión")
                            .setMessage(errorMessage)
                            .setPositiveButton("Aceptar") { _, _ ->
                                // Acciones adicionales si es necesario
                            }
                            .show()

                        Log.e("API_CALL_ERROR", errorMessage, t)
                    }
                }
            })
        }
    }
}

