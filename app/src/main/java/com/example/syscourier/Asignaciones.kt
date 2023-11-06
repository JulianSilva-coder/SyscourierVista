package com.example.syscourier

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.syscourier.databinding.FragmentAsignacionesBinding
import com.google.zxing.integration.android.IntentIntegrator

data class MyObject(val name: String, val description: String)

class Asignaciones : Fragment() {

    private lateinit var binding: FragmentAsignacionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsignacionesBinding.inflate(inflater, container, false)
        binding.camaraEscaner.setOnClickListener { initScanner() }

        // Crear una lista de objetos de ejemplo
        val data = listOf(
            MyObject("Objeto 1", "Descripción del objeto 1"),
            MyObject("Objeto 2", "Descripción del objeto 2"),
            MyObject("Objeto 3", "Descripción del objeto 3")
        )

        // Inflar vistas para cada elemento de la lista de objetos
        for (item in data) {
            val itemView = inflater.inflate(R.layout.item_layout, container, false)

            val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)

            nameTextView.text = item.name
            descriptionTextView.text = item.description

            binding.asignacionesLayout.addView(itemView)
        }

        return binding.root
    }

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el codigo de barras para conocer la informacion brindada!")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "El valor es: ${result.contents}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}