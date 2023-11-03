package com.example.syscourier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.buttonlogin)
        loginButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Menudesplegable::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val service = ApiClient.retrofit.create(ApiService::class.java)
            val call = service.obtenerDatos(1)

            call.enqueue(object : Callback<GuiaIntro> {
                override fun onResponse(call: Call<GuiaIntro>, response: Response<GuiaIntro>) {
                    if (response.isSuccessful) {
                        val objetoResultado = response.body()
                        // Aquí puedes manejar la respuesta del servicio web
                        Log.d("Respuesta del servicio", objetoResultado.toString())
                        // Agrega el intent para pasar a la siguiente actividad aquí, dentro del bloque de respuesta exitosa
                        val intent = Intent(this@MainActivity, Menudesplegable::class.java)
                        startActivity(intent)
                    } else {
                        // Manejar el caso de respuesta fallida
                        // Por ejemplo, mostrar un mensaje de error
                        Toast.makeText(this@MainActivity, "Error en la respuesta del servicio web", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GuiaIntro>, t: Throwable) {
                    // Manejar errores de red u otros errores
                    // Por ejemplo, mostrar un mensaje de error
                    Toast.makeText(this@MainActivity, "Error de red: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }



    }
}