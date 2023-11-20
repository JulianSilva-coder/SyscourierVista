package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Entregados_infoFragment
import com.example.syscourier.R

/**
 * [Entregados_infoActivity] es una actividad que muestra información detallada
 * sobre un elemento entregado específico.
 * Reemplaza el fragmento [Entregados_infoFragment] en el layout correspondiente
 * para mostrar los detalles del elemento entregado correspondiente al ID proporcionado.
 */
class Entregados_infoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = "" // ¡Atención! Este valor podría necesitar ser ajustado a un valor específico
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entregados_activity_info)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        // Inicia una transacción de fragmentos para mostrar detalles del elemento entregado
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = Entregados_infoFragment()
        val args = Bundle()
        args.putInt(Entregados_infoFragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args
        fragmentTransaction.replace(R.id.entregadosLayout, fragment)
        fragmentTransaction.commit()
    }
}
