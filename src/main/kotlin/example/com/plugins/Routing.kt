package example.com.plugins

import example.com.dto.*
import example.com.repository.MarketItemRepository
import example.com.repository.PostRepository
import example.com.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val marketItemRepository = MarketItemRepository()
    val postRepository = PostRepository()

    // user
    routing {
        get("/") {
            call.respondText("Api User funcionando")
        }
        // getUserEmail
        get("/users/{email}") {
            try {
                val email = call.request.queryParameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val response = userRepository.getUserEmail(email)
                val userResponse = response?.toUserResponse()
                if (userResponse == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(userResponse)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

        //deleteUser
        delete("/users/{email}") {
            try {
                val email = call.request.queryParameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                if (userRepository.delete(email)) {
                    call.respondText("Usuário deletado", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao deletar usuario", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao deletar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updateEmail
        patch("/users/{email}") {
            try {
                val email = call.request.queryParameters["email"]
                val newName = call.request.queryParameters["newName"]
                if (email == null || newName == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@patch
                }
                if (userRepository.updateEmail(email, newName)) {
                    call.respondText("Usuário atualizado", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar usuario", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updatePassword
        patch("/users/{email}/password") {
            try {
                val email = call.request.queryParameters["email"]
                val newPassword = call.request.queryParameters["newPassword"]
                if (email == null || newPassword == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@patch
                }
                if (userRepository.updatePassword(email, newPassword)) {
                    call.respondText("Usuário atualizado", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar usuario", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

        // insertUSer
        post("/users") {
            try {
                val request = call.receive<UserRequest>()
                val user = request.toUser()
                userRepository.save(user)?.let {
                    call.respondText("Usuário gravado", status = HttpStatusCode.Created)
                } ?: call.respondText("Erro ao gravar usuario", status = HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // marketItem
    routing {
        get("/marketItem") {
            call.respondText("MarketItem disponível")
        }
        get("/marketItem/all") {
            val response = marketItemRepository.getAll().map {
                it.toMarketItemResponse()
            }
            call.respond(response)
        }

        get("/marketItem/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val response = marketItemRepository.getById(id)
            call.respond(response)
        }

        post("/marketItem") {
            try {
                val request = call.receive<MarketItemRequest>()
                marketItemRepository.save(request.toMarketItem())?.let {
                    call.respondText("Item gravado", status = HttpStatusCode.Created)
                } ?: call.respondText("Erro ao gravar item", status = HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar item $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // post
    routing {
        get("/post") {
            call.respondText("Post disponível")
        }

        get("/posts/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val response = postRepository.getById(id)
            if (response == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(response.toPostResponse())
        }

        get("/posts") {
            val response = postRepository.getAll().map {
                it.toPostResponse()
            }
            call.respond(response)
        }

        put("/posts") {
            try {
                val request = call.receive<PostRequest>()
                postRepository.save(request.toPost())?.let {
                    call.respondText("Post gravado", status = HttpStatusCode.Created)
                } ?: call.respondText("Erro ao gravar post", status = HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar post $e", status = HttpStatusCode.BadRequest)
            }
        }

        delete("/posts/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            postRepository.delete(id)
            call.respondText("Post deletado", status = HttpStatusCode.OK)
        }
    }
}