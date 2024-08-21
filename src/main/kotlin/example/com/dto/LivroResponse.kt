package example.com.dto

import example.com.model.Livro
import kotlinx.serialization.Serializable


@Serializable
data class LivroResponse(
    val id: Int,
    val ano: Int,
    val autor: String,
    val descricao: String,
    val genero: String,
    val imagem: String,
    val paginas: Int,
    val titulo: String
)

fun Livro.toLivroResponse() = LivroResponse(
    id = id,
    ano = ano,
    autor = autor,
    descricao = descricao,
    genero = genero,
    imagem = imagem,
    paginas = paginas,
    titulo = titulo
)