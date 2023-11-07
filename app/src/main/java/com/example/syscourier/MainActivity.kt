package com.example.syscourier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
            val usuarioEditText = findViewById<EditText>(R.id.editTextText)
            val contrasenaEditText = findViewById<EditText>(R.id.editTextTextPassword)

            val usuario = usuarioEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()

            Log.d("USUARIO", usuario)
            Log.d("CONTRASENA", contrasena)

            val service = ApiClient.retrofit.create(ApiService::class.java)
            val call = service.obtenerDatos(usuario, contrasena)

            call.enqueue(object : Callback<GuiaIntro> {
                override fun onResponse(call: Call<GuiaIntro>, response: Response<GuiaIntro>) {
                    if(response.isSuccessful){
                        val token = response.body()
                        Log.d("Token", token.toString())
                        val intent = Intent(this@MainActivity, Menudesplegable::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity, "Error de inicio de sesión, credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GuiaIntro>, t: Throwable) {
                    val errorMessage = "Error de red: " + t.message
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("API_CALL_ERROR", errorMessage)
                }
            })
        }
    }
}

