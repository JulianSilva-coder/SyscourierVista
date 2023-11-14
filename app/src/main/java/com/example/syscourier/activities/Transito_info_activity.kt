package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.R
import com.example.syscourier.fragments.Transporte

class Transito_info_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transporte_activity)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.idAsignacion, Transporte())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.idValueTextView, Transporte())
            .commit()
    }
}