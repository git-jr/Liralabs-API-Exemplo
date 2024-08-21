package example.com.dto

import example.com.model.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusRequest(
    val idStatus: Int,
    val status: String
) {
    fun toStatus() = Status(
        idStatus = idStatus,
        status = status
    )
}