/**
 * Fragmento encargado de mostrar las devoluciones realizadas.
 * Muestra una lista de devoluciones obtenidas desde el servidor y las presenta en un RecyclerView.
 * Utiliza un adaptador personalizado (AdapterDevoluciones) para visualizar la información de las devoluciones.
 * Se comunica con el servidor para obtener los datos necesarios y manejar posibles errores de red.
 *
 * @param name El nombre de la devolución.
 * @param description La descripción de la devolución.
 * @param id El identificador único de la devolución.
 *
 * @property binding Variable que almacena la referencia al diseño (layout) asociado a este fragmento.
 * @constructor Crea una instancia de Devoluciones.
 *
 * @author Julian Silva
 */
package com.example.syscourier.fragments

import android.os.AsyncTask
import com.example.syscourier.adapters.AdapterDevoluciones
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
import com.example.syscourier.adapters.Adapterentregados
import com.example.syscourier.databinding.DevolucionesFragmentBinding
import com.example.syscourier.dto.GuiaIntroDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

data class MydevolucionesObject(val name: String, val description: String, val id: Int)

class Devoluciones : Fragment() {
    private lateinit var binding: DevolucionesFragmentBinding
    /**
     * Crea y devuelve la vista asociada con el fragmento.
     *
     * @param inflater El objeto LayoutInflater que se utiliza para inflar cualquier vista en el fragmento.
     * @param container Si no es nulo, este es el padre al que se adjuntará la vista resultante.
     * @param savedInstanceState Si no es nulo, este fragmento está siendo reconstruido a partir de un estado guardado anteriormente.
     *
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DevolucionesFragmentBinding.inflate(inflater, container, false)

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiasIntro/7")
                Log.d("Resultado_Devoluciones", result.toString())

                // Verificar si el fragmento aún está adjunto a la actividad
                if (isAdded) {
                    // Crear el adaptador
                    val adapter = AdapterDevoluciones(result, requireContext())

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

    /**
     * Realiza una solicitud GET para obtener las devoluciones desde el servidor.
     *
     * @param url La URL del servidor desde donde se obtendrán las devoluciones.
     *
     * @return Una lista de objetos GuiaIntroDTO que representan las devoluciones obtenidas.
     */
    private fun makeGetRequest(url: String): List<GuiaIntroDTO> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Authorization", MiApp.accessToken)
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        if (response.body == null) {
            Toast.makeText(requireContext(), "No hay devoluciones", Toast.LENGTH_SHORT).show()
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
    /**
     * Maneja los errores de red mostrando un mensaje de alerta al usuario.
     *
     * @param exception La excepción que representa el error de red.
     */
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
