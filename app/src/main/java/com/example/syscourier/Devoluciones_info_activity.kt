package com.example.syscourier

import Devoluciones_info
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Devoluciones_info_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_devoluciones)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.devolucionesLayout, Devoluciones_info())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.devolucionesLayout, Devoluciones_info())
            .commit()
    }
}