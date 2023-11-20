package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.R
import com.example.syscourier.fragments.AsignacionesInfoItemFragment
import com.example.syscourier.fragments.scanner_Info_fragment

class scanner_info_activity: AppCompatActivity() {
    companion object {
        const val EXTRA_ID_GUIA = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.asignaciones_activity_info_item)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)
        // Crear una lista de objetos de ejemplo


        // Puedes agregar el fragmento en el contenedor correspondiente
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Crea una instancia del fragmento y establece los argumentos
        val fragment = scanner_Info_fragment()
        val args = Bundle()
        args.putInt(scanner_Info_fragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args

        // Reemplaza el fragmento en el contenedor
        fragmentTransaction.replace(R.id.asignaciones_info_item, fragment)
        fragmentTransaction.commit()
    }
}