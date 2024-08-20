package example.com.dto

import example.com.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val name: String,
    val email: String,
    val password: String,
)

fun User.toUserResponse() = UserResponse(
    name = name,
    email = email,
    password = password,
)