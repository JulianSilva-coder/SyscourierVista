package com.example.syscourier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Entregados_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entregados)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.entregados, Entregados())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.entregados, Entregados())
            .commit()
    }
}