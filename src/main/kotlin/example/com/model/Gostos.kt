package example.com.model

import org.jetbrains.exposed.sql.Table

data class Gosto(
    val idUser: Int,
    val idIngrediente: Int,
    val escolha: String
)

object Gostos : Table() {
    val idUser = integer("idUser")
    val idIngrediente = integer("idIngrediente")
    val escolha = text("escolha")

    override val primaryKey = PrimaryKey(idUser)
}
