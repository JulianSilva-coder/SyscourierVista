package com.example.syscourier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class entregados_infoActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_entregados)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.entregadosLayout, Entregados_info())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.entregadosLayout, Entregados_info())
            .commit()
    }
}