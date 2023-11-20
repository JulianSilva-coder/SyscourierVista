/**
 * Fragmento encargado de manejar y mostrar las asignaciones disponibles para un usuario.
 * Permite escanear códigos de barras para obtener información específica de una asignación.
 * Utiliza un RecyclerView para mostrar las asignaciones disponibles.
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.MiApp
import com.example.syscourier.activities.AsignacionesInfoItemActivity
import com.example.syscourier.activities.TransporteInfoItemActivity
import com.example.syscourier.activities.scanner_info_activity
import com.example.syscourier.adapters.AsignacionesAdapter
import com.example.syscourier.databinding.AsignacionesFragmentBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import okhttp3.OkHttpClient
import okhttp3.Request

class Asignaciones : Fragment() {

    private lateinit var binding: AsignacionesFragmentBinding
    /**
     * Función que se llama para crear y devolver la vista asociada con el fragmento.
     * Configura el RecyclerView para mostrar las asignaciones disponibles.
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
        binding = AsignacionesFragmentBinding.inflate(inflater, container, false)
        binding.camaraEscaner.setOnClickListener { initScanner() }

        // Crear una lista de objetos de ejemplo
        AsyncTask.execute {
            try {
                val result = makeGetRequest(MiApp.BASE_URL + "guiasIntro/1")
                Log.d("Resultado", result.toString())

                // Verificar si el fragmento aún está adjunto a la actividad
                if (isAdded) {
                    // Crear el adaptador
                    val adapter = AsignacionesAdapter(result, requireContext())

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
     * Realiza una solicitud GET para obtener la lista de asignaciones del servidor.
     *
     * @param url La URL del servidor desde donde se obtendrán las asignaciones.
     *
     * @return La lista de objetos GuiaIntroDTO obtenidos del servidor.
     *
     * @author Nicolas Peña
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
    /**
     * Maneja los errores de red mostrando un mensaje de alerta al usuario.
     *
     * @param exception La excepción que representa el error de red.
     *
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
     * Inicializa el escáner de códigos de barras utilizando la biblioteca ZXing.
     * Muestra la información asociada al código de barras escaneado.
     * @author Julian Silva
     */
    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el código de barras para conocer la información brindada!")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    /**
     * Método que maneja el resultado del escaneo de códigos de barras.
     * Muestra información o realiza acciones basadas en el código de barras escaneado.
     *
     * @param requestCode El código de solicitud original pasado a startActivityForResult().
     * @param resultCode El código de resultado devuelto por la actividad.
     * @param data Un Intent opcional con datos resultantes.
     * @author Julian Silva
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(requireContext(), "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                val scannedCode = result.contents
                Toast.makeText(
                    requireContext(),
                    "El valor es: $scannedCode",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(requireContext(), scanner_info_activity::class.java)
                intent.putExtra(scanner_info_activity.EXTRA_ID_GUIA, scannedCode.toInt())
                requireContext().startActivity(intent)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
