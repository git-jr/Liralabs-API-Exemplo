package example.com.dto

import example.com.model.Petshop
import kotlinx.serialization.Serializable

@Serializable
data class PetshopResponse(
    val uid: Int,
    var name: String,
    var imagem: String,
    var localizacao: String,
    var descricao: String,
    var servicos: String,
    val cnpj: String
)

fun Petshop.toPetshopResponse() = PetshopResponse(
    uid = this.uid,
    name = this.name,
    imagem = this.imagem,
    localizacao = this.localizacao,
    descricao = this.descricao,
    servicos = this.servicos,
    cnpj = this.cnpj
)