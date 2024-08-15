package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String,
    val password: String,
    val email: String
) {
    fun toUser() = User(
        name = name,
        password = password,
        email = email
    )
}