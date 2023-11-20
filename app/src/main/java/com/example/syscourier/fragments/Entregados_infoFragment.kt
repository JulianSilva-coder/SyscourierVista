
package com.example.syscourier.fragments
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
/**
 * Fragmento que muestra información relacionada con entregas.
 * Permite capturar imágenes, registrar observaciones y realizar cambios de estado en la aplicación.
 * @property EXTRA_ID_GUIA Identificador extra para la guía.
 * @property CAMERA_REQUEST_CODE Código de solicitud para la cámara.
 * @property imagesUploaded Contador de imágenes cargadas.
 * @property MAX_IMAGES_ALLOWED Número máximo de imágenes permitidas.
 */
class Entregados_infoFragment : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    private val CAMERA_REQUEST_CODE = 1
    private var imagesUploaded = 0
    private val MAX_IMAGES_ALLOWED = 1

    /**
     * Crea y devuelve la vista asociada al fragmento.
     * @param inflater El objeto inflater que se utilizará para inflar el layout.
     * @param container El contenedor en el que se debe inflar la vista.
     * @param savedInstanceState El estado previamente guardado del fragmento.
     * @return La vista asociada al fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.entregados_fragment_info, container, false)
        return vista
    }
    /**
     * Se llama inmediatamente después de que onCreateView() ha terminado de ser llamado.
     * Configura los listeners de los botones y gestiona las acciones correspondientes.
     * @param view La vista devuelta por onCreateView().
     * @param savedInstanceState El estado previamente guardado del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.foto_camera).setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
        }

        view.findViewById<Button>(R.id.button_entrega).setOnClickListener {
            var campoTextoObservacion: TextView = view.findViewById(R.id.edit_text_area)

            // Verificar si el campo de texto está vacío
            val campoTextoVacio = campoTextoObservacion.text.isEmpty()

            // Verificar si no se ha subido ninguna imagen
            val ningunaImagenSubida = imagesUploaded == 0

            // Mostrar mensaje si alguna de las condiciones se cumple
            if (campoTextoVacio || ningunaImagenSubida) {
                var mensaje = ""
                if (campoTextoVacio && ningunaImagenSubida) {
                    mensaje =
                        "Es necesario llenar el campo de texto de observaciones y subir una imagen"
                } else if (campoTextoVacio) {
                    mensaje = "Es necesario llenar el campo de texto de observaciones"
                } else if (ningunaImagenSubida) {
                    mensaje = "Es necesario subir una imagen"
                }
                showMessage(mensaje)
            } else {
                val guiaId = arguments?.getInt(EXTRA_ID_GUIA, -1) ?: -1
                AsyncTask.execute {
                    try {
                        makePutRequest(
                            MiApp.BASE_URL + "cambioestado",
                            guiaId,
                            campoTextoObservacion.text.toString()
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
    /**
     * Realiza una solicitud PUT al servidor con información para cambiar el estado de una guía.
     * @param url La URL a la que se enviará la solicitud PUT.
     * @param guiaId El ID de la guía.
     * @param observaciones Las observaciones relacionadas con el cambio de estado.
     */
    private fun makePutRequest(url: String, guiaId: Int, observaciones: String) {
        val client = OkHttpClient()
        val cambioEstado = CambioEstadoDTO(
            guiaId = guiaId,
            codEstado = 6,
            motivo = "Entregado",
            observaciones = observaciones
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
    /**
     * Maneja errores de red, mostrando un diálogo de error al usuario.
     * @param exception La excepción que se produjo durante la operación de red.
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
     * Muestra un mensaje en un diálogo de alerta.
     * @param mensaje El mensaje que se mostrará en el diálogo.
     */
    private fun showMessage(mensaje: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext())
                .setTitle("Informacion")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar") { _, _ ->
                    // Acciones adicionales si es necesario
                }
                .show()
        }
    }
    /**
     * Abre la cámara para capturar una imagen.
     */
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    /**
     * Gestiona el resultado de la actividad de la cámara.
     * @param requestCode El código de solicitud.
     * @param resultCode El código de resultado.
     * @param data Los datos devueltos por la actividad.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveImageToInternalStorage(imageBitmap)
        }
    }
    /**
     * Guarda la imagen capturada en el almacenamiento interno del dispositivo.
     * @param bitmap El mapa de bits de la imagen capturada.
     */
    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        val wrapper = ContextWrapper(requireContext())
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${System.currentTimeMillis()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

            // Agregar la imagen a la galería
            addToGallery(file)

            Log.d("ImageSaved", "Image saved successfully: ${file.absolutePath}")

            view?.findViewById<TextView>(R.id.info_imagencargada)?.text = file.name

            // Incrementar el contador de imágenes después de guardar la imagen
            saveImageAndIncrementCounter(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ImageSaveError", "Error saving image: ${e.message}")
        }
    }
    /**
     * Agrega la imagen capturada a la galería del dispositivo.
     * @param file El archivo de imagen.
     */
    private fun addToGallery(file: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        requireContext().sendBroadcast(mediaScanIntent)
    }
    /**
     * Guarda la imagen y aumenta el contador de imágenes cargadas.
     * @param bitmap El mapa de bits de la imagen.
     */
    private fun saveImageAndIncrementCounter(bitmap: Bitmap) {
        // Código para guardar la imagen

        // Incrementar el contador de imágenes
        imagesUploaded++
    }
}
