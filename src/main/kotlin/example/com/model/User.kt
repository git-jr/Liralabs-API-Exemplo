package example.com.model

import kotlinx.serialization.Serializable

@Serializable
 data class User(
    val name: String,
    val password: String,
    val email: String
)