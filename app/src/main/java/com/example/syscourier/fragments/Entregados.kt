package com.example.syscourier.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.MiApp
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.adapters.Adapterentregados
import com.example.syscourier.adapters.AsignacionesAdapter
import com.example.syscourier.databinding.EntregadosFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request


class Entregados : Fragment() {
    private lateinit var binding: EntregadosFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EntregadosFragmentBinding.inflate(inflater, container, false)

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiasIntro/6")
                Log.d("Resultado_Entregas", result.toString())

                // Verificar si el fragmento aún está adjunto a la actividad
                if (isAdded) {
                    // Crear el adaptador
                    val adapter = Adapterentregados(result, requireContext())

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
                handleNetworkError(e)
            }
        }
        return binding.root
    }

    private fun makeGetRequest(url: String): List<GuiaIntroDTO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Authorization", MiApp.accessToken)
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        if (response.body == null) {
            Toast.makeText(requireContext(), "No hay entregas", Toast.LENGTH_SHORT).show()
            return emptyList()
        }

        // El cuerpo de la respuesta no es nulo, procede con la conversión
        val responseBody = response.body!!.string()

        // Usa Gson u otra biblioteca para convertir la cadena JSON a una lista de objetos GuiaIntro
        val gson = Gson()
        val guiasIntroType = object : TypeToken<List<GuiaIntroDTO>>() {}.type

        // Verifica si la cadena JSON no es nula antes de intentar la conversión
        return if (responseBody.isNotEmpty()) {
            gson.fromJson(responseBody, guiasIntroType)
        } else {
            emptyList()
        }
    }

    private fun handleNetworkError(exception: Exception) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext())
                .setTitle("Error de conexión")
                .setMessage("Tiempo de espera agotado. Verifica tu conexión a Internet.")
                .setPositiveButton("Aceptar") { _, _ ->
                    // Acciones adicionales si es necesario
                }
                .show()

            Log.e("NETWORK_ERROR", exception.message, exception)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}