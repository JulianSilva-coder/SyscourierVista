package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.R
import com.example.syscourier.fragments.TransporteInfoItemFragment

/**
 * [TransporteInfoItemActivity] es una actividad encargada de mostrar información detallada
 * relacionada con un elemento específico de transporte.
 * Reemplaza el fragmento [TransporteInfoItemFragment] en el layout correspondiente
 * para mostrar los detalles del elemento de transporte correspondiente al ID proporcionado.
 */
class TransporteInfoItemActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = "" // ¡Atención! Este valor podría necesitar ser ajustado a un valor específico
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transporte_activity_info_item)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        // Inicia una transacción de fragmentos para mostrar detalles del elemento de transporte
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Crea una instancia del fragmento y establece los argumentos
        val fragment = TransporteInfoItemFragment()
        val args = Bundle()
        args.putInt(TransporteInfoItemFragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args

        // Reemplaza el fragmento en el contenedor
        fragmentTransaction.replace(R.id.info_item_transporte, fragment)
        fragmentTransaction.commit()
    }
}
