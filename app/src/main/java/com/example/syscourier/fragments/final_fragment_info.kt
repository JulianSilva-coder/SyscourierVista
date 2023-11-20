package com.example.syscourier.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.dto.CambioEstadoDTO
import com.example.syscourier.dto.ErrorDTO
import com.example.syscourier.dto.GuiaInfoDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class final_fragment_info : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = "extra_id_guia"
    }
    /**
     * Crea la vista del fragmento y realiza la solicitud de información sobre la guía de envío.
     * @param inflater El objeto LayoutInflater que se utiliza para inflar la vista del fragmento.
     * @param container El ViewGroup al que se adjuntará la vista del fragmento.
     * @param savedInstanceState El estado previamente guardado de la actividad.
     * @return La vista inflada del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.final_fragment_info_item, container, false)

        // Obtener los elementos de la interfaz de usuario por sus identificadores
        val final_idValueTextView: TextView =
            view.findViewById(R.id.final_info_idValueTextView)
        val final_productoValueTextView: TextView =
            view.findViewById(R.id.final_info_productoValueTextView)
        val final_remitenteInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_remitenteInfoValueTextView)
        val final_nombreRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_nombreRemitenteInfoValueTextView)
        val final_direccionRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_direccionRemitenteInfoValueTextView)
        val final_telefonoRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_telefonoRemitenteInfoValueTextView)
        val final_identificacionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_identificacionDestinatarioInfoValueTextView)
        val final_nombreDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_nombreDestinatarioInfoValueTextView)
        val final_direccionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_direccionDestinatarioInfoValueTextView)
        val final_telefonoDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_telefonoDestinatarioInfoValueTextView)
        val final_totalInfoValueTextView: TextView =
            view.findViewById(R.id.final_info_totalInfoValueTextView)


        // Obtener el guiaId de los argumentos
        val guiaId = arguments?.getInt(EXTRA_ID_GUIA, -1) ?: -1

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiainfo/${guiaId}")
                Log.d("Resultado", result.toString())
                activity?.runOnUiThread {
                    final_idValueTextView.text = result.id.toString()
                    final_productoValueTextView.text = result.producto
                    final_remitenteInfoValueTextView.text = result.remitenteIdentificacion
                    final_nombreRemitenteInfoValueTextView.text = result.remitenteNombre
                    final_direccionRemitenteInfoValueTextView.text =
                        result.remitenteDireccion
                    final_telefonoRemitenteInfoValueTextView.text = result.remitenteTelefono
                    final_identificacionDestinatarioInfoValueTextView.text =
                        result.destinatarioIdentificacion
                    final_nombreDestinatarioInfoValueTextView.text =
                        result.destinatarioNombre
                    final_direccionDestinatarioInfoValueTextView.text =
                        result.destinatarioDireccion
                    final_telefonoDestinatarioInfoValueTextView.text =
                        result.destinatarioTelefono
                    final_totalInfoValueTextView.text = result.totalFlete.toString()

                }
            } catch (e: Exception) {
                handleNetworkError(e)
            }
        }

        return view
    }

    /**
     * Realiza una solicitud GET para obtener información de una guía de envío.
     * @param url La URL a la que se realizará la solicitud GET.
     * @return Un objeto GuiaInfoDTO que contiene la información de la guía de envío.
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
     * Maneja errores de red mostrando un diálogo de error en la UI principal.
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
    /**
     * Realiza una solicitud PUT para actualizar el estado de una guía de envío.
     * @param url La URL a la que se realizará la solicitud PUT.
     * @param guiaId El ID de la guía que se actualizará.
     * @param observaciones Observaciones relacionadas con el cambio de estado.
     */
    private fun makePutRequest(url: String, guiaId: Int, observaciones: String) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(
            guiaId = guiaId,
            codEstado = 6,
            motivo = "Entregados",
            observaciones = observaciones
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