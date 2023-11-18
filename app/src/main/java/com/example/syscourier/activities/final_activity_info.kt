package com.example.syscourier.activities

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.fragments.AsignacionesInfoItemFragment
import com.example.syscourier.fragments.final_fragment_info
import okhttp3.OkHttpClient
import okhttp3.Request

class final_activity_info: AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_activity_info_item)

        val guiaId = intent.getIntExtra(AsignacionesInfoItemActivity.EXTRA_ID_GUIA, -1)
        // Crear una lista de objetos de ejemplo



        // Puedes agregar el fragmento en el contenedor correspondiente
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
