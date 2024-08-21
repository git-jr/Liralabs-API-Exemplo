package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val uid: String,
    val cpf: String,
    val name: String,
    val email: String,
    val password: String,
    val imagem: String
) {
    fun toUser(): User {
        return User(
            uid = uid,
            cpf = cpf,
            name = name,
            email = email,
            password = password,
            imagem = imagem
        )
    }
}