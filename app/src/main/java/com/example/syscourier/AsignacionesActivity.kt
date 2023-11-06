package com.example.syscourier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AsignacionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignaciones)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.asignacionesLayout, Asignaciones())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.asignacionesLayout, Asignaciones())
            .commit()
    }
}