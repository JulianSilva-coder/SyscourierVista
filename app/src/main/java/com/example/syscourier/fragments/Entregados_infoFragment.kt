package com.example.syscourier.fragments

import android.Manifest
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.TimeUnit

class Entregados_infoFragment : Fragment() {

    companion object {
        const val EXTRA_ID_GUIA = ""
    }

    private val CAMERA_REQUEST_CODE = 1
    private var imagesUploaded = 0
    private val MAX_IMAGES_ALLOWED = 1

    private lateinit var file_to_upload: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.entregados_fragment_info, container, false)
        return vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.foto_camera).setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
        }

        view.findViewById<Button>(R.id.button_entrega).setOnClickListener {
            var campoTextoObservacion: TextView = view.findViewById(R.id.edit_text_area)

            val campoTextoVacio = campoTextoObservacion.text.isEmpty()

            val ningunaImagenSubida = imagesUploaded == 0

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
    private fun makePutRequest(url: String, guiaId: Int, observaciones: String) {
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

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
                .addHeader("Authorization", MiApp.accessToken)
                .url(url)
                .put(requestBody)
                .build()

            val response = client.newCall(request).execute()
            Log.d("CODIGO!!!", response.code.toString())

            if (response.code == 202) {
                CoroutineScope(Dispatchers.Main).launch {
                    uploadFileToFTP(file_to_upload)
                }
            } else {
                val responseBody =
                    response.body?.string() ?: throw RuntimeException("Error en la solicitud")
                val gson = Gson()
                val mensaje =
                    gson.fromJson(responseBody, ErrorDTO::class.java)?.message ?: "Mensaje nulo o vacío"

                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("Error", "Error en la solicitud: ${e.message}")
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun handleNetworkError(exception: Exception) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext())
                .setTitle("Error de conexión")
                .setMessage("Tiempo de espera agotado. Verifica tu conexión a Internet.")
                .setPositiveButton("Aceptar") { _, _ ->
                }
                .show()

            Log.e("NETWORK_ERROR", exception.message, exception)
        }
    }

    private fun showMessage(mensaje: String) {
        requireActivity().runOnUiThread {
            AlertDialog.Builder(requireContext())
                .setTitle("Informacion")
                .setMessage(mensaje)
                .setPositiveButton("Aceptar") { _, _ ->
                }
                .show()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveImageToInternalStorage(imageBitmap)
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        val wrapper = ContextWrapper(requireContext())
        var dir = wrapper.getDir("images", Context.MODE_PRIVATE)
        file_to_upload = File(dir, "${System.currentTimeMillis()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file_to_upload)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()

            addToGallery(file_to_upload)

            Log.d("ImageSaved", "Image saved successfully: ${file_to_upload.absolutePath}")

            view?.findViewById<TextView>(R.id.info_imagencargada)?.text = file_to_upload.name

            saveImageAndIncrementCounter(bitmap)

        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ImageSaveError", "Error saving image: ${e.message}")
        }
    }

    private fun addToGallery(file: File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        requireContext().sendBroadcast(mediaScanIntent)
    }

    private fun saveImageAndIncrementCounter(bitmap: Bitmap) {
        imagesUploaded++
    }

    private suspend fun uploadFileToFTP(file: File) {
        withContext(Dispatchers.IO) {
            val server = MiApp.HOST_NAME
            val port = 21
            val user = "syscourierftp"
            val password = "Syscourier2023."

            val ftpClient = FTPClient()
            ftpClient.connectTimeout = 10000

            try {
                ftpClient.connect(server, port)
                ftpClient.login(user, password)
                ftpClient.enterLocalPassiveMode()
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE)

                val inputStream: InputStream = FileInputStream(file)

                val remoteFile = "/home/syscourierftp/${file.name}"
                val storeSuccess = ftpClient.storeFile(remoteFile, inputStream)
                inputStream.close()

                if (storeSuccess) {
                    Log.d("FTP", "File uploaded successfully to $remoteFile")
                } else {
                    Log.d("FTP", "Failed to upload file")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("FTP", "Error uploading file: ${e.message}")
            } finally {
                try {
                    ftpClient.disconnect()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
