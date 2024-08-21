package example.com.model

import org.jetbrains.exposed.sql.Table

data class Ingrediente(
    val idIngrediente: Int = 0,
    val nomeIngrediente: String
)

object Ingredientes : Table() {
    val idIngrediente = integer("idIngrediente").autoIncrement()
    val nomeIngrediente = text("nomeIngrediente")

    override val primaryKey = PrimaryKey(idIngrediente)
}
