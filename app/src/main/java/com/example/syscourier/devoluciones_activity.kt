package com.example.syscourier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class devoluciones_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devoluciones)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.devoluciones, Devoluciones())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.devoluciones, Devoluciones())
            .commit()
    }
}