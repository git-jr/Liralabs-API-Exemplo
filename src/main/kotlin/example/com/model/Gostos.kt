package example.com.model

import org.jetbrains.exposed.sql.Table

data class Gosto(
    val idGostos: Int,
    val idUser: Int,
    val idIngrediente: Int
)


object Gostos : Table() {
    val idGostos = integer("idGostos").autoIncrement()
    val idUser = integer("idUser")
    val idIngrediente = integer("idIngrediente")

    override val primaryKey = PrimaryKey(idGostos)
}
