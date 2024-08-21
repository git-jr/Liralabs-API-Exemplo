package example.com.model

import org.jetbrains.exposed.sql.Table

data class Dieta(
    val idDieta: Int,
    val idUser: Int,
    val idReceita: Int
)

object Dietas : Table() {
    val idDieta = integer("idDieta").autoIncrement()
    val idUser = integer("idUser")
    val idReceita = integer("idReceita")

    override val primaryKey = PrimaryKey(idDieta)
}