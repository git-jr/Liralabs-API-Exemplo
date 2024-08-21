package example.com.dto

import example.com.model.Pet
import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class PetResponse(
    val id: Long = 0,
    var name: String,
    var descricao: String,
    var peso: String,
    var idade: String,
    var imagem: String
)

fun Pet.toPetResponse() = PetResponse(
    id = this.id,
    name = this.name,
    descricao = this.descricao,
    peso = this.peso,
    idade = this.idade,
    imagem = this.imagem
)