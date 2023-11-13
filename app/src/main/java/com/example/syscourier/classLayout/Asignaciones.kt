package com.example.syscourier.classLayout

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.GuiaIntro
import com.example.syscourier.MiApp
import com.example.syscourier.adapters.MyAdapter
import com.example.syscourier.databinding.FragmentAsignacionesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import okhttp3.OkHttpClient
import okhttp3.Request


class Asignaciones : Fragment() {

    private lateinit var binding: FragmentAsignacionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsignacionesBinding.inflate(inflater, container, false)
        binding.camaraEscaner.setOnClickListener { initScanner() }

        // Crear una lista de objetos de ejemplo

        AsyncTask.execute {
            try {
                val result = makeGetRequest("http://18.221.165.81:80/guiasIntro/1")
                Log.d("Resultado", result.toString())

                // Configurar el RecyclerView en el hilo principal
                requireActivity().runOnUiThread {
                    // Crear el adaptador
                    val adapter = MyAdapter(result)

                    // Configurar el RecyclerView solo si el adaptador no es nulo
                    if (binding.recyclerView.adapter == null) {
                        // Configurar el RecyclerView
                        val recyclerView = binding.recyclerView
                        recyclerView.layoutManager =
                            LinearLayoutManager(requireContext()) // Asegura la dirección vertical
                        recyclerView.adapter = adapter
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



/*
        val data = listOf(
            MyObject("Objeto 1", "Descripción del objeto 1", 1234567),
            MyObject("Objeto 2", "Descripción del objeto 2", 987654),
            MyObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MyObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MyObject("Objeto 3", "Descripción del objeto 3", 1234556)
        )*/



        return binding.root
    }

    private fun makeGetRequest(url: String): List<GuiaIntro> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader(
                "Authorization", MiApp.accessToken
            )
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        val responseBody =
            response.body?.string() ?: throw RuntimeException("Error en la solicitud")

        // Usa Gson u otra biblioteca para convertir la cadena JSON a una lista de objetos GuiaIntro
        val gson = Gson()
        val guiasIntroType = object : TypeToken<List<GuiaIntro>>() {}.type
        return gson.fromJson(responseBody, guiasIntroType)
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