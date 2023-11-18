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
import androidx.fragment.app.Fragment
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.activities.AsignacionesInfoItemActivity
import com.example.syscourier.activities.Devoluciones_info_activity
import com.example.syscourier.activities.Entregados_infoActivity
import com.example.syscourier.activities.Menudesplegable
import com.example.syscourier.dto.CambioEstadoDTO
import com.example.syscourier.dto.ErrorDTO
import com.example.syscourier.dto.GuiaInfoDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class TransporteInfoItemFragment : Fragment(){
    companion object {
        const val EXTRA_ID_GUIA = ""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.transporte_fragment_info_item, container, false)

        // Obtener los elementos de la interfaz de usuario por sus identificadores
        val transporte_idValueTextView: TextView =
            view.findViewById(R.id.transporte_idValueTextView)
        val transporte_productoValueTextView: TextView =
            view.findViewById(R.id.transporte_productoValueTextView)
        val transporte_remitenteInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_remitenteInfoValueTextView)
        val transporte_nombreRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_nombreRemitenteInfoValueTextView)
        val transporte_direccionRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_direccionRemitenteInfoValueTextView)
        val transporte_telefonoRemitenteInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_telefonoRemitenteInfoValueTextView)
        val transporte_identificacionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_identificacionDestinatarioInfoValueTextView)
        val transporte_nombreDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_nombreDestinatarioInfoValueTextView)
        val transporte_direccionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_direccionDestinatarioInfoValueTextView)
        val transporte_telefonoDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_telefonoDestinatarioInfoValueTextView)
        val transporte_totalInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_totalInfoValueTextView)
        val transporte_Entregar_button: Button = view.findViewById(R.id.transporte_Entregar)
        val transporte_devolucion_button: Button = view.findViewById(R.id.transporte_devolucion)


        // Obtener el guiaId de los argumentos
        val guiaId = arguments?.getInt(TransporteInfoItemFragment.EXTRA_ID_GUIA, -1) ?: -1

        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiainfo/${guiaId}")
                Log.d("Resultado", result.toString())
                activity?.runOnUiThread {
                    transporte_idValueTextView.text = result.id.toString()
                    transporte_productoValueTextView.text = result.producto
                    transporte_remitenteInfoValueTextView.text = result.remitenteIdentificacion
                    transporte_nombreRemitenteInfoValueTextView.text = result.remitenteNombre
                    transporte_direccionRemitenteInfoValueTextView.text =
                        result.remitenteDireccion
                    transporte_telefonoRemitenteInfoValueTextView.text = result.remitenteTelefono
                    transporte_identificacionDestinatarioInfoValueTextView.text =
                        result.destinatarioIdentificacion
                    transporte_nombreDestinatarioInfoValueTextView.text =
                        result.destinatarioNombre
                    transporte_direccionDestinatarioInfoValueTextView.text =
                        result.destinatarioDireccion
                    transporte_telefonoDestinatarioInfoValueTextView.text =
                        result.destinatarioTelefono
                    transporte_totalInfoValueTextView.text = result.totalFlete.toString()


                    transporte_Entregar_button.setOnClickListener {
                        val intent = Intent(context, Entregados_infoActivity::class.java)
                        intent.putExtra(Entregados_infoActivity.EXTRA_ID_GUIA, guiaId)
                        requireContext().startActivity(intent)
                    }
                    transporte_devolucion_button.setOnClickListener {
                        val intent = Intent(context, Devoluciones_info_activity::class.java)
                        intent.putExtra(Devoluciones_info_activity.EXTRA_ID_GUIA, guiaId)
                        requireContext().startActivity(intent)
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