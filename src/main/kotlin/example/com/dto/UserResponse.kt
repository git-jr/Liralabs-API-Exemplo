package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val name: String,
    val password: String,
    val email: String
)

fun User.toUserResponse() = UserResponse(
    id = id.toString(),
    name = name,
    password = password,
    email = email
)