package com.example.syscourier.classLayout

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.GuiaIntro
import com.example.syscourier.MiApp
import com.example.syscourier.activitys.info_fragment_item_transporte_Activity
import com.example.syscourier.adapters.MyAdapter
import com.example.syscourier.databinding.FragmentAsignacionesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import okhttp3.OkHttpClient
import okhttp3.Request

class Asignaciones : Fragment() {

    private lateinit var binding: FragmentAsignacionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAsignacionesBinding.inflate(inflater, container, false)
        binding.camaraEscaner.setOnClickListener { initScanner() }

        // Crear una lista de objetos de ejemplo
        AsyncTask.execute {
            try {
                val result = makeGetRequest("http://18.221.165.81:80/guiasIntro/1")
                Log.d("Resultado", result.toString())

                // Verificar si el fragmento aún está adjunto a la actividad
                if (isAdded) {
                    // Crear el adaptador
                    val adapter = MyAdapter(result, requireContext())

                    // Configurar el RecyclerView solo si el adaptador no es nulo
                    if (binding.recyclerView != null && binding.recyclerView.adapter == null) {
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
                e.printStackTrace()
            }
        }

        return binding.root
    }

    private fun makeGetRequest(url: String): List<GuiaIntro> {
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
        val guiasIntroType = object : TypeToken<List<GuiaIntro>>() {}.type
        return gson.fromJson(responseBody, guiasIntroType)
    }

    private fun initScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanea el código de barras para conocer la información brindada!")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

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

                // Verifica si el código escaneado coincide con algún criterio
                if (codigoCoincide(scannedCode)) {
                    // Si coincide, inicia la nueva actividad
                    val intent = Intent(requireContext(), info_fragment_item_transporte_Activity::class.java)
                    startActivity(intent)
                } else {
                    // Si no coincide, puedes realizar alguna acción adicional o simplemente mostrar un mensaje
                    Toast.makeText(
                        requireContext(),
                        "El código escaneado no coincide con el criterio deseado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // Función para verificar si el código escaneado coincide con algún criterio
    private fun codigoCoincide(scannedCode: String): Boolean {
        // Puedes ajustar esta condición según tus necesidades
        return scannedCode == "231009206610000163"
    }

}
