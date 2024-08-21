package example.com.dto

import example.com.model.Receita
import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class ReceitaRequest(
    val idReceita: Int = 0,
    val imagem: String,
    val nome: String,
    val tipo: String,
    val url: String,
) {
    fun toReceita() = Receita(
        idReceita = idReceita,
        imagem = imagem,
        nome = nome,
        tipo = tipo,
        url = url
    )
}