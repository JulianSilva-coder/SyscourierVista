package com.example.syscourier.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.syscourier.MiApp
import com.example.syscourier.R
import com.example.syscourier.activities.Menudesplegable
import com.example.syscourier.dto.CambioEstadoDTO
import com.example.syscourier.dto.ErrorDTO
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Devoluciones_info_item_Fragment : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.devoluciones_fragment_info, container, false)
        return vista

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener referencia al Spinner en tu diseño
        val spinner: Spinner = view.findViewById(R.id.spinner)
        view.findViewById<Button>(R.id.Boton_devolucion).setOnClickListener {
            var campoTextoDevolucion: TextView = view.findViewById(R.id.edit_text_area)
            val campovacioD = campoTextoDevolucion.text.isEmpty()
            val selectedItem = spinner.selectedItem.toString()

            var mensaje = ""

            if (campovacioD || selectedItem == "Seleccionar la razon de la devolucion") {
                if (campovacioD && selectedItem == "Seleccionar la razon de la devolucion") {
                    mensaje = "Es necesario llenar el campo y seleccionar una razón"
                } else if (campovacioD) {
                    mensaje = "Es necesario no dejar el campo vacío"
                } else if (selectedItem == "Seleccionar la razon de la devolucion") {
                    mensaje = "Es necesario seleccionar alguna razón"
                }
                showMessage(mensaje)
            } else {

                val guiaId = arguments?.getInt(Entregados_infoFragment.EXTRA_ID_GUIA, -1) ?: -1
                AsyncTask.execute {
                    try {
                        makePutRequest(
                            MiApp.BASE_URL + "cambioestado",
                            guiaId,
                            campoTextoDevolucion.text.toString()
                        )
                        val intent = Intent(requireContext(), Menudesplegable::class.java)
                        requireContext().startActivity(intent)
                    } catch (e: Exception) {
                        handleNetworkError(e)
                    }
                }
            }
        }
    }

    private fun makePutRequest(url: String, guiaId: Int, devoluciones: String) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(
            guiaId = guiaId,
            codEstado = 7,
            motivo = "Devolucion",
            observaciones = devoluciones
        )
        val gson = Gson()
        Log.d("ID: ", guiaId.toString())
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

    private fun showMessage(mensaje: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext()).setTitle("Informacion").setMessage(mensaje)
                .setPositiveButton("Aceptar") { _, _ ->
                    // Acciones adicionales si es necesario
                }.show()
        }
    }
}
