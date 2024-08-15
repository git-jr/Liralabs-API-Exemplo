package example.com.plugins

import example.com.dto.UserRequest
import example.com.dto.toUserResponse
import example.com.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repository = UserRepository()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/users") {
            val response = repository.users.map {
                it.toUserResponse()
            }
            call.respond(response)
        }

        post("/users") {
            try {
                val request = call.receive<UserRequest>()
                repository.save(request.toUser())
                call.respondText("Usario gravado", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }
    }
}