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

        get("/1") {
            call.respondText("Teste 1")
        }

        get("/users") {
            call.respondText(repository.users.toString())
        }

        post("/users") {
            val user = call.receive<User>()
            repository.save(user)
            call.respondText("User stored correctly", status = HttpStatusCode.Created)
        }
    }
}
