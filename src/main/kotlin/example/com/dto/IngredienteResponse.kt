package example.com.dto

import example.com.model.Ingrediente
import kotlinx.serialization.Serializable

@Serializable
data class IngredienteResponse(
    val idIngrediente: Int,
    val nomeIngrediente: String
)

fun Ingrediente.toIngredienteResponse() = IngredienteResponse(
    idIngrediente = this.idIngrediente,
    nomeIngrediente = this.nomeIngrediente
)