package com.example.syscourier.activities

import com.example.syscourier.fragments.Devoluciones_info_item_Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.R
import com.example.syscourier.fragments.Entregados_infoFragment

class Devoluciones_info_activity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devoluciones_activity_info)

        val guiaId = intent.getIntExtra(Entregados_infoActivity.EXTRA_ID_GUIA, -1)


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = Devoluciones_info_item_Fragment()
        val args = Bundle()
        args.putInt(Devoluciones_info_item_Fragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args
        fragmentTransaction.replace(R.id.devolucionesLayout, fragment)
        fragmentTransaction.commit()
    }
}