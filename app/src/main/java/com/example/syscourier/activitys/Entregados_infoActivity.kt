package com.example.syscourier.activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.Entregados_info
import com.example.syscourier.R

class entregados_infoActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_entregados)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.entregadosLayout, Entregados_info())
        fragmentTransaction.commit()
    }
}