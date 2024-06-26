/**
 * Fragmento encargado de mostrar información detallada de una asignación específica.
 * Permite visualizar información detallada y realizar acciones como cambiar el estado de la asignación.
 * Utiliza un diseño de fragmento con elementos de interfaz de usuario para mostrar los detalles de la asignación.
 *
 * @author Julian Silva
 */

package com.example.syscourier.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.activities.Menudesplegable
import com.example.syscourier.dto.CambioEstadoDTO
import com.example.syscourier.dto.ErrorDTO
import com.example.syscourier.dto.GuiaInfoDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class AsignacionesInfoItemFragment : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = "extra_id_guia"
    }
    /**
     * Función llamada para crear y devolver la vista asociada con el fragmento.
     * Infla el diseño del fragmento y muestra los detalles de la asignación.
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
    ): View? {// Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.asignaciones_fragment_info_item, container, false)

        // Obtener los elementos de la interfaz de usuario por sus identificadores
        val asignaciones_idValueTextView: TextView =
            view.findViewById(R.id.asignaciones_idValueTextView)
        val asignaciones_identificacionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_identificacionDestinatarioInfoValueTextView)
        val asignaciones_nombreDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_nombreDestinatarioInfoValueTextView)
        val asignaciones_direccionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_direccionDestinatarioInfoValueTextView)
        val asignaciones_telefonoDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_telefonoDestinatarioInfoValueTextView)
        val asignaciones_info_button: Button = view.findViewById(R.id.asignaciones_info_button)


        // Obtener el guiaId de los argumentos
        val guiaId = arguments?.getInt(EXTRA_ID_GUIA, -1) ?: -1

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiainfo/${guiaId}")
                Log.d("Resultado", result.toString())
                activity?.runOnUiThread {
                    asignaciones_idValueTextView.text = result.id.toString()
                    asignaciones_identificacionDestinatarioInfoValueTextView.text =
                        result.destinatarioIdentificacion
                    asignaciones_nombreDestinatarioInfoValueTextView.text =
                        result.destinatarioNombre
                    asignaciones_direccionDestinatarioInfoValueTextView.text =
                        result.destinatarioDireccion
                    asignaciones_telefonoDestinatarioInfoValueTextView.text =
                        result.destinatarioTelefono
                    

                    asignaciones_info_button.setOnClickListener {
                        AsyncTask.execute {
                            try {
                                makePutRequest(MiApp.BASE_URL + "cambioestado", guiaId)
                                val intent = Intent(requireContext(), Menudesplegable::class.java)
                                requireContext().startActivity(intent)
                            } catch (e: Exception) {
                                handleNetworkError(e)
                            }
                        }
                    }

                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }

        return view
    }
    /**
     * Realiza una solicitud GET para obtener información detallada de la asignación desde el servidor.
     *
     * @param url La URL del servidor desde donde se obtendrán los detalles de la asignación.
     *
     * @return El objeto GuiaInfoDTO que contiene los detalles de la asignación.
     */
    private fun makeGetRequest(url: String): GuiaInfoDTO {
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
        return gson.fromJson(responseBody, GuiaInfoDTO::class.java)
    }
    /**
     * Maneja los errores de red mostrando un mensaje de alerta al usuario.
     * @param exception La excepción que representa el error de red.
     * @author Nicolas Peña
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
    /**
     * Realiza una solicitud PUT para cambiar el estado de la asignación en el servidor.
     *
     * @param url La URL del servidor donde se realizará la solicitud de cambio de estado.
     * @param guiaId El ID de la asignación para cambiar su estado.
     *
     * @author Nicolas Peña
     */
    private fun makePutRequest(url: String, guiaId: Int) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(
            guiaId = guiaId,
            codEstado = 1,
            motivo = "En asignaciones",
            observaciones = "En asignaciones"
        )
        val gson = Gson()
        val cambioEstadoJson = gson.toJson(cambioEstado)
        val requestBody =
            cambioEstadoJson.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .addHeader(
                "Authorization", MiApp.accessToken
            )
            .url(url)
            .put(requestBody)
            .build()

        val response = client.newCall(request).execute()
        Log.d("CODIGO!!!", response.code.toString())

        if (response.code == 202) {
            // Código para el caso 202
        } else {
            val responseBody =
                response.body?.string() ?: throw RuntimeException("Error en la solicitud")
            // Usa Gson u otra biblioteca para convertir la cadena JSON a una lista de objetos GuiaIntro
            val gson = Gson()
            val mensaje =
                gson.fromJson(responseBody, ErrorDTO::class.java)?.message ?: "Mensaje nulo o vacío"

            // Mostrar el Toast en el hilo principal
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }
}