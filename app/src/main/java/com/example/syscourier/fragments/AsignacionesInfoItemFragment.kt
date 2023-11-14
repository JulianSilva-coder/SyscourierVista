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
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.activities.AsignacionesInfoItemActivity
import com.example.syscourier.activities.Menudesplegable
import com.example.syscourier.activities.Transito_info_activity
import com.example.syscourier.dto.CambioEstadoDTO
import com.example.syscourier.dto.ErrorDTO
import com.example.syscourier.dto.GuiaInfoDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class AsignacionesInfoItemFragment : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = "extra_id_guia"
    }

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
                val result = makeGetRequest("http://18.221.165.81:80/guiainfo/${guiaId}")
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
                            makePutRequest("http://18.221.165.81:80/cambioestado", guiaId)
                            val intent = Intent(requireContext(), Menudesplegable::class.java)
                            requireContext().startActivity(intent)
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }

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

    private fun makePutRequest(url: String, guiaId: Int) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(guiaId = guiaId, codEstado = 8, motivo = "En transporte", observaciones = "En transporte")
        val gson = Gson()
        val cambioEstadoJson = gson.toJson(cambioEstado)
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), cambioEstadoJson)
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
            val mensaje = gson.fromJson(responseBody, ErrorDTO::class.java)?.message ?: "Mensaje nulo o vacío"

            // Mostrar el Toast en el hilo principal
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }
}