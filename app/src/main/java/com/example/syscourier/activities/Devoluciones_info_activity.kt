package com.example.syscourier.activities

import com.example.syscourier.fragments.Devoluciones_info_item_Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.R

class Devoluciones_info_activity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devoluciones_activity_info)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.devolucionesLayout, Devoluciones_info_item_Fragment())
        fragmentTransaction.commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.devolucionesLayout, Devoluciones_info_item_Fragment())
            .commit()
    }
}