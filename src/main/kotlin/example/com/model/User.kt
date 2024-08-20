package example.com.model

import org.jetbrains.exposed.sql.Table

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val img: String
)

object Users : Table() {
    val id = text("id")
    val name = text("name")
    val email = text("email")
    val password = text("password")
    val img = text("img")

    override val primaryKey = PrimaryKey(id)
}