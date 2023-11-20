package com.example.syscourier.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
/**
 * Fragmento que muestra información detallada de una asignación mediante solicitudes HTTP.
 * Permite visualizar datos del remitente, destinatario y realizar actualizaciones.
 * Autor: Julian Silva
 * Fecha de creación: 20 de noviembre de 2023
 */
class scanner_Info_fragment: Fragment() {
    companion object {
        const val EXTRA_ID_GUIA = "extra_id_guia"
    }
    /**
     * Infla la vista del fragmento y realiza operaciones de red para obtener información de la asignación.
     *
     * @param inflater El inflater utilizado para inflar la vista del fragmento.
     * @param container El contenedor donde se inserta la vista del fragmento.
     * @param savedInstanceState El estado previamente guardado del fragmento, si existe.
     * @return La vista inflada del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.asignaciones_fragment_info_item, container, false)

        // Obtener los elementos de la interfaz de usuario por sus identificadores
        val asignaciones_idValueTextView: TextView =
            view.findViewById(R.id.asignaciones_idValueTextView)
        val asignaciones_productoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_productoValueTextView)
        val asignaciones_remitenteInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_remitenteInfoValueTextView)
        val asignaciones_nombreRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_nombreRemitenteInfoValueTextView)
        val asignaciones_direccionRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_direccionRemitenteInfoValueTextView)
        val asignaciones_telefonoRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_telefonoRemitenteInfoValueTextView)
        val asignaciones_identificacionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_identificacionDestinatarioInfoValueTextView)
        val asignaciones_nombreDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_nombreDestinatarioInfoValueTextView)
        val asignaciones_direccionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_direccionDestinatarioInfoValueTextView)
        val asignaciones_telefonoDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_telefonoDestinatarioInfoValueTextView)
        val asignaciones_totalInfoValueTextView: TextView =
            view.findViewById(R.id.asignaciones_totalInfoValueTextView)
        val asignaciones_info_button: Button = view.findViewById(R.id.asignaciones_info_button)


        // Obtener el guiaId de los argumentos
        val guiaId = arguments?.getInt(EXTRA_ID_GUIA, -1) ?: -1

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiainfo/${guiaId}")
                Log.d("Resultado", result.toString())
                activity?.runOnUiThread {
                    asignaciones_idValueTextView.text = result.id.toString()
                    asignaciones_productoValueTextView.text = result.producto
                    asignaciones_remitenteInfoValueTextView.text = result.remitenteIdentificacion
                    asignaciones_nombreRemitenteInfoValueTextView.text = result.remitenteNombre
                    asignaciones_direccionRemitenteInfoValueTextView.text =
                        result.remitenteDireccion
                    asignaciones_telefonoRemitenteInfoValueTextView.text = result.remitenteTelefono
                    asignaciones_identificacionDestinatarioInfoValueTextView.text =
                        result.destinatarioIdentificacion
                    asignaciones_nombreDestinatarioInfoValueTextView.text =
                        result.destinatarioNombre
                    asignaciones_direccionDestinatarioInfoValueTextView.text =
                        result.destinatarioDireccion
                    asignaciones_telefonoDestinatarioInfoValueTextView.text =
                        result.destinatarioTelefono
                    asignaciones_totalInfoValueTextView.text = result.totalFlete.toString()


                    asignaciones_info_button.setOnClickListener {
                        AsyncTask.execute {
                            try {
                                makePutRequest(MiApp.BASE_URL + "asignacion?id=${guiaId}")
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
     * Realiza una solicitud GET a la URL proporcionada y devuelve un objeto GuiaInfoDTO.
     *
     * @param url La URL a la que se realizará la solicitud GET.
     * @return Un objeto GuiaInfoDTO obtenido como respuesta de la solicitud.
     * @throws RuntimeException Si hay algún error en la solicitud.
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
     * Maneja los errores de red mostrando un mensaje de error al usuario y registrando el error en los logs.
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
    /**
     * Realiza una solicitud PUT a la URL proporcionada y maneja la respuesta según el código de estado.
     *
     * @param url La URL a la que se realizará la solicitud PUT.
     */
    private fun makePutRequest(url: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .addHeader(
                "Authorization", MiApp.accessToken
            )
            .url(url)
            .put(RequestBody.create(null, byteArrayOf()))
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