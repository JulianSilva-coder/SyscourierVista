package com.example.syscourier.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.MiApp
import com.example.syscourier.adapters.AsignacionesAdapter
import com.example.syscourier.adapters.TransporteAdapter
import com.example.syscourier.databinding.TransporteFragmentBinding
import com.example.syscourier.dto.GuiaIntroDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class Transporte : Fragment() {
    private lateinit var binding: TransporteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransporteFragmentBinding.inflate(inflater, container, false)

        AsyncTask.execute {
            try {
                val result = makeGetRequest("http://18.221.165.81:80/guiasIntro/8")
                Log.d("Resultado", result.toString())

                // Verificar si el fragmento aún está adjunto a la actividad
                if (isAdded) {
                    // Crear el adaptador
                    val adapter = TransporteAdapter(result, requireContext())

                    // Configurar el RecyclerView solo si el adaptador no es nulo
                    if (binding.recyclerView.adapter == null) {
                        // Utilizar post para asegurarse de que la configuración se realice en el hilo principal
                        binding.recyclerView.post {
                            val recyclerView = binding.recyclerView
                            recyclerView.layoutManager =
                                LinearLayoutManager(requireContext()) // Asegura la dirección vertical
                            recyclerView.adapter = adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return binding.root
    }

    private fun makeGetRequest(url: String): List<GuiaIntroDTO> {
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
        val guiasIntroType = object : TypeToken<List<GuiaIntroDTO>>() {}.type
        return gson.fromJson(responseBody, guiasIntroType)
    }
}
