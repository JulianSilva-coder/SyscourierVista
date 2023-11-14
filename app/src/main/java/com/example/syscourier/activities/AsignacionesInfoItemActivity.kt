package com.example.syscourier.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.dto.GuiaInfoDTO
import com.example.syscourier.fragments.AsignacionesInfoItemFragment
import com.example.syscourier.fragments.info_fragment_item
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class AsignacionesInfoItemActivity : AppCompatActivity() {

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
        val fragment = AsignacionesInfoItemFragment()
        val args = Bundle()
        args.putInt(AsignacionesInfoItemFragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args

        // Reemplaza el fragmento en el contenedor
        fragmentTransaction.replace(R.id.asignaciones_info_item, fragment)
        fragmentTransaction.commit()
    }


}
