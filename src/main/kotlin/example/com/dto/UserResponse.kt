package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val uid: String,
    val cpf: String,
    val name: String,
    val email: String,
    val password: String,
    val imagem: String
)

fun User.toUserResponse() = UserResponse(
    uid = this.uid,
    cpf = this.cpf,
    name = this.name,
    email = this.email,
    password = this.password,
    imagem = this.imagem
)