package example.com.dto

import example.com.model.Receita
import kotlinx.serialization.Serializable

@Serializable
data class ReceitaResponse(
    val idReceita: Int = 0,
    val imagem: String,
    val nome: String,
    val tipo: String,
    val url: String,
)

fun Receita.toReceitaResponse() = ReceitaResponse(
    idReceita = this.idReceita,
    imagem = this.imagem,
    nome = this.nome,
    tipo = this.tipo,
    url = this.url
)