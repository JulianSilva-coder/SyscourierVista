package com.example.syscourier.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.syscourier.fragments.Entregados_infoFragment
import com.example.syscourier.R
class Entregados_infoActivity : AppCompatActivity()  {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entregados_activity_info)

        val guiaId = intent.getIntExtra(EXTRA_ID_GUIA, -1)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = Entregados_infoFragment()
        val args = Bundle()
        args.putInt(Entregados_infoFragment.EXTRA_ID_GUIA, guiaId)
        fragment.arguments = args
        fragmentTransaction.replace(R.id.entregadosLayout, fragment)
        fragmentTransaction.commit()
    }
}