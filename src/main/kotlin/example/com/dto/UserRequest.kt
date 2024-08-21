package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val idUser: Int,
    val name: String,
    val email: String,
    val password: String,
    val date: String,
    val image: String
) {
    fun toUser() = User(
        idUser = idUser,
        name = name,
        email = email,
        password = password,
        date = date,
        image = image
    )
}