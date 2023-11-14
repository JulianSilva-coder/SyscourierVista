package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Devoluciones
import com.example.syscourier.R

class devoluciones_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devoluciones_activity)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentdevoluciones, Devoluciones())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentdevoluciones, Devoluciones())
            .commit()
    }
}