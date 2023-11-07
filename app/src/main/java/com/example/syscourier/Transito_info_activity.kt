package com.example.syscourier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Transito_info_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transito)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.TransitoLayout, Transito_info())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.TransitoLayout, Transito_info())
            .commit()
    }
}