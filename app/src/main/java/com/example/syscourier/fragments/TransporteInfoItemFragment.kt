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
import com.example.syscourier.activities.Devoluciones_info_activity
import com.example.syscourier.activities.Entregados_infoActivity
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

    /**
     * Infla y devuelve la vista del fragmento con los datos del elemento de transporte.
     *
     * @param inflater El LayoutInflater utilizado para inflar la vista.
     * @param container El ViewGroup donde se va a mostrar la vista.
     * @param savedInstanceState El estado guardado de la instancia.
     * @return La vista inflada del fragmento.
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {// Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.transporte_fragment_info_item, container, false)

        // Obtener los elementos de la interfaz de usuario por sus identificadores
        val transporte_idValueTextView: TextView =
            view.findViewById(R.id.transporte_idValueTextView)
        val transporte_identificacionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_identificacionDestinatarioInfoValueTextView)
        val transporte_nombreDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_nombreDestinatarioInfoValueTextView)
        val transporte_direccionDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_direccionDestinatarioInfoValueTextView)
        val transporte_telefonoDestinatarioInfoValueTextView: TextView =
            view.findViewById(R.id.transporte_telefonoDestinatarioInfoValueTextView)
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
                    transporte_identificacionDestinatarioInfoValueTextView.text =
                        result.destinatarioIdentificacion
                    transporte_nombreDestinatarioInfoValueTextView.text =
                        result.destinatarioNombre
                    transporte_direccionDestinatarioInfoValueTextView.text =
                        result.destinatarioDireccion
                    transporte_telefonoDestinatarioInfoValueTextView.text =
                        result.destinatarioTelefono


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
     * Realiza una solicitud PUT a la URL proporcionada para cambiar el estado de una guía.
     *
     * @param url La URL a la que se realizará la solicitud PUT.
     * @param guiaId El identificador de la guía a la que se cambiará el estado.
     */
    private fun makePutRequest(url: String, guiaId: Int) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(guiaId = guiaId, codEstado = 6, motivo = "En entrega", observaciones = "En entrega")
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