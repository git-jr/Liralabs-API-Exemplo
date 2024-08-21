package example.com.dto

import example.com.model.StatusLivro
import kotlinx.serialization.Serializable

@Serializable
data class StatusLivroResponse(
    val idStatusLivro: Int,
    val idLivro: Int,
    val idStatus: Int,
    val email: String,
    val paginaslidas: Int
)

fun StatusLivro.toStatusLivroResponse() = StatusLivroResponse(
    idStatusLivro = idStatusLivro,
    idLivro = idLivro,
    idStatus = idStatus,
    email = email,
    paginaslidas = paginaslidas
)