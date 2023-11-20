package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.R
import com.example.syscourier.fragments.final_fragment_info

/**
 * [final_activity_info] es una actividad encargada de mostrar información final
 * sobre una guía específica.
 * Reemplaza el fragmento [final_fragment_info] en el layout correspondiente
 * para mostrar los detalles finales de la guía correspondiente al ID proporcionado.
 */
class final_activity_info : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = "" // ¡Atención! Este valor podría necesitar ser ajustado a un valor específico
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity_info_item)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        // Inicia una transacción de fragmentos para mostrar detalles finales de la guía
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Crea una instancia del fragmento y establece los argumentos
        val fragment = final_fragment_info()
        val args = Bundle()
        args.putInt(final_fragment_info.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args

        // Reemplaza el fragmento en el contenedor
        fragmentTransaction.replace(R.id.final_info_layout, fragment)
        fragmentTransaction.commit()
    }
}
