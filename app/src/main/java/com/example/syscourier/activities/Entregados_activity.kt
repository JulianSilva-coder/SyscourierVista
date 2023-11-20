package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Entregados
import com.example.syscourier.R

/**
 * [Entregados_activity] es una actividad encargada de mostrar la lista de elementos entregados.
 * Reemplaza el fragmento [Entregados] en el layout correspondiente para mostrar la lista de elementos entregados.
 */
class Entregados_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entregados_activity)

        // Inicia una transacción de fragmentos para mostrar la lista de elementos entregados
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentEntregados, Entregados())
        fragmentTransaction.commit()
    }
}
