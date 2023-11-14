package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Entregados
import com.example.syscourier.R

class Entregados_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entregados_activity)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentEntregados, Entregados())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentEntregados, Entregados())
            .commit()
    }
}