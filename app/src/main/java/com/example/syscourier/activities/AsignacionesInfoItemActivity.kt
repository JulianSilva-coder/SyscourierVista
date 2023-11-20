package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.R
import com.example.syscourier.fragments.AsignacionesInfoItemFragment

/**
 * [AsignacionesInfoItemActivity] es una actividad que muestra información detallada de una guía.
 * Esta actividad crea un [AsignacionesInfoItemFragment] para mostrar la información detallada
 * de la guía correspondiente.
 */
class AsignacionesInfoItemActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = "" // ¡Atención! Este valor podría necesitar ser ajustado a un valor específico
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asignaciones_activity_info_item)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        // Se inicia una transacción de fragmentos para mostrar los detalles de la guía
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Crea una instancia del fragmento y establece los argumentos
        val fragment = AsignacionesInfoItemFragment()
        val args = Bundle()
        args.putInt(AsignacionesInfoItemFragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args

        // Reemplaza el fragmento en el contenedor
        fragmentTransaction.replace(R.id.asignaciones_info_item, fragment)
        fragmentTransaction.commit()
    }
}
