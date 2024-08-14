package example.com.plugins

import example.com.model.User
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
            call.respond(repository.users)
        }

        post("/users") {
            val user = call.receive<User>()
            repository.save(user)
            call.respondText("Usario gravado", status = HttpStatusCode.Created)
        }
    }
}
