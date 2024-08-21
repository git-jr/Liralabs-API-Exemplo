package example.com.model

import org.jetbrains.exposed.sql.Table

data class User(
    val idUser: Int,
    val name: String,
    val email: String,
    val password: String,
    val date: String,
    val image: String
)

object Users : Table() {
    val idUser = integer("idUser").autoIncrement()
    val name = text("name")
    val email = text("email")
    val password = text("password")
    val date = text("date")
    val image = text("image")

    override val primaryKey = PrimaryKey(idUser)
}