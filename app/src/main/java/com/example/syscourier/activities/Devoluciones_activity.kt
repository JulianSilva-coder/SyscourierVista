package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Devoluciones
import com.example.syscourier.R

/**
 * [devoluciones_activity] es una actividad encargada de mostrar la interfaz
 * de usuario para gestionar devoluciones.
 * Reemplaza el fragmento [Devoluciones] en el layout correspondiente para mostrar
 * la funcionalidad de gestión de devoluciones.
 */
class devoluciones_activity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devoluciones_activity)

        // Inicia una transacción de fragmentos para mostrar el fragmento de devoluciones
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentdevoluciones, Devoluciones())
        fragmentTransaction.commit()
    }
}
