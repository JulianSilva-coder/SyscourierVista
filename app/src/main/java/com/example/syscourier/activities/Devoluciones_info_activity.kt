package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.R
import com.example.syscourier.fragments.Devoluciones_info_item_Fragment

/**
 * [Devoluciones_info_activity] es una actividad encargada de mostrar información detallada
 * sobre una devolución específica.
 * Reemplaza el fragmento [Devoluciones_info_item_Fragment] en el layout correspondiente
 * para mostrar los detalles de la devolución correspondiente al ID proporcionado.
 */
class Devoluciones_info_activity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = "" // ¡Atención! Este valor podría necesitar ser ajustado a un valor específico
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devoluciones_activity_info)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        // Inicia una transacción de fragmentos para mostrar detalles de la devolución
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = Devoluciones_info_item_Fragment()
        val args = Bundle()
        args.putInt(Devoluciones_info_item_Fragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args
        fragmentTransaction.replace(R.id.devolucionesLayout, fragment)
        fragmentTransaction.commit()
    }
}
