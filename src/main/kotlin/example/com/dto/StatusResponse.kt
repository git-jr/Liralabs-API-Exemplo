package example.com.dto

import example.com.model.Status
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val idStatus: Int,
    val status: String
)

fun Status.toResponse() = StatusResponse(
    idStatus = idStatus,
    status = status
)