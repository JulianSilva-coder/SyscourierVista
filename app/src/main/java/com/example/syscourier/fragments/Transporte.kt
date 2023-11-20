package com.example.syscourier.fragments
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

/**
 * Fragmento que muestra una lista de elementos relacionados con transporte obtenidos mediante
 * una solicitud HTTP.
 */
class Transporte : Fragment() {
    private lateinit var binding: TransporteFragmentBinding
    /**
     * Infla la vista del fragmento y muestra la lista de elementos relacionados con transporte
     * obtenidos mediante una solicitud HTTP.
     *
     * @param inflater El inflater utilizado para inflar la vista del fragmento.
     * @param container El contenedor donde se inserta la vista del fragmento.
     * @param savedInstanceState El estado previamente guardado del fragmento, si existe.
     * @return La vista inflada del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TransporteFragmentBinding.inflate(inflater, container, false)

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiasIntro/8")
                Log.d("Resultado", result.toString())

                requireActivity().runOnUiThread {
                    // Verificar si el fragmento aún está adjunto a la actividad
                    if (isAdded) {
                        // Crear el adaptador
                        val adapter = TransporteAdapter(result, requireContext())

                        // Configurar el RecyclerView solo si el adaptador no es nulo
                        if (binding.recyclerView.adapter == null) {
                            binding.recyclerView.post {
                                val recyclerView = binding.recyclerView
                                recyclerView.layoutManager =
                                    LinearLayoutManager(requireContext()) // Asegura la dirección vertical
                                recyclerView.adapter = adapter
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return binding.root
    }
    /**
     * Realiza una solicitud GET a la URL proporcionada y devuelve una lista de objetos GuiaIntroDTO.
     *
     * @param url La URL a la que se realizará la solicitud GET.
     * @return Una lista de objetos GuiaIntroDTO obtenida como respuesta de la solicitud.
     */
    private fun makeGetRequest(url: String): List<GuiaIntroDTO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Authorization", MiApp.accessToken)
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        if (response.body == null) {
            Toast.makeText(requireContext(), "No hay asignaciones", Toast.LENGTH_SHORT).show()
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
}
