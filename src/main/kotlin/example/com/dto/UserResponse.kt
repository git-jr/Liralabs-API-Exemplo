package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val idUser: Int,
    val name: String,
    val email: String,
    val password: String,
    val date: String,
    val image: String
)

fun User.toUserResponse() = UserResponse(
    idUser = this.idUser,
    name = this.name,
    email = this.email,
    password = this.password,
    date = this.date,
    image = this.image
)