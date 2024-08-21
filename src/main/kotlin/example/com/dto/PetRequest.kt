package example.com.dto

import example.com.model.Pet
import kotlinx.serialization.Serializable

@Serializable
data class PetRequest(
    val id: Long,
    var name: String,
    var descricao: String,
    var peso: String,
    var idade: String,
    var imagem: String
) {
    fun toPet(): Pet {
        return Pet(
            id = id,
            name = name,
            descricao = descricao,
            peso = peso,
            idade = idade,
            imagem = imagem
        )
    }
}