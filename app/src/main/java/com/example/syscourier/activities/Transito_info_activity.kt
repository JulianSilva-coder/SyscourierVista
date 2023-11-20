package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.R
import com.example.syscourier.fragments.Transporte

/**
 * [Transito_info_activity] es una actividad encargada de mostrar información
 * relacionada con el transporte.
 * Reemplaza el fragmento [Transporte] en el layout correspondiente
 * para mostrar información sobre el transporte.
 */
class Transito_info_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transporte_activity)

        // Inicia una transacción de fragmentos para mostrar información sobre el transporte
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.transporteLayout, Transporte())
        fragmentTransaction.commit()
    }
}
