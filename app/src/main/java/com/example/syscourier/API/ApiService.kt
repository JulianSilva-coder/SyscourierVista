import com.example.syscourier.dto.TokenDTO
import com.example.syscourier.dto.UsuarioDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    /**
     * Realiza una solicitud POST al endpoint "loginMensajero" para obtener los datos del usuario.
     *
     * @param credentials Objeto JSON que contiene el correo electrónico y la contraseña del usuario.
     * @return Un objeto [Call] que representa la solicitud asíncrona con el token del usuario.
     */
    interface ApiService {
        @POST("login")
        fun obtenerDatos(
            @Body credenciales: UsuarioDTO
        ): Call<TokenDTO>
    }
}