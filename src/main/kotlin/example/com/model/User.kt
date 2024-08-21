package example.com.model

import org.jetbrains.exposed.sql.Table

data class User(
    val uid: String,
    val cpf: String,
    val name: String,
    val email: String,
    val password: String,
    val imagem: String
)

object Users : Table() {
    val uid = text("uid")
    val cpf = text("cpf")
    val name = text("name")
    val email = text("email")
    val password = text("password")
    val imagem = text("imagem")

    override val primaryKey = PrimaryKey(uid)
}