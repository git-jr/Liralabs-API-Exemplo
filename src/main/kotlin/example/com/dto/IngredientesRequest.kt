package example.com.dto

import example.com.model.Ingrediente
import example.com.model.Receita
import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class IngredienteRequest(
    val idIngrediente: Int = 0,
    val nomeIngrediente: String
) {
    fun toIngrediente() = Ingrediente(
        idIngrediente = idIngrediente,
        nomeIngrediente = nomeIngrediente
    )
}