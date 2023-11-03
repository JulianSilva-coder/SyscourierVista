package com.example.syscourier

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.syscourier.databinding.FragmentAsignacionesBinding
import com.google.zxing.integration.android.IntentIntegrator

class Asignaciones : Fragment() {

    private lateinit var binding: FragmentAsignacionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsignacionesBinding.inflate(inflater, container, false)
        binding.camaraEscaner.setOnClickListener { initScanner() }
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
        if(result != null){
            if(result.contents == null){
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "El valor es: ${result.contents}", Toast.LENGTH_SHORT).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}