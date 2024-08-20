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

        // verificar se senha o usuario é igual a senha do banco, usando o email
        get("/users/login") {
            try {
                val email = call.request.queryParameters["email"]
                val password = call.request.queryParameters["password"]
                if (email == null || password == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val response = userRepository.getUserByEmailAndPassword(email, password)
                call.respond(response.toUserResponse())
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

        get("/users") {
            val response = userRepository.getAll().map {
                it.toUserResponse()
            }
            call.respond(response)
        }

        get("/users/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val response = userRepository.getById(id)
            if (response == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(response.toUserResponse())
        }

        patch("/users/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }
            try {
                val request = call.receive<UserRequest>()
                val requestUser = request.toUser()
                userRepository.updateUser(requestUser).let {
                    call.respondText("Usuário atualizado", status = HttpStatusCode.OK)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar usuario $e", status = HttpStatusCode.BadRequest)
            }
        }

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

        delete("/users/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            if (userRepository.delete(id)) {
                call.respondText("Usuário deletado", status = HttpStatusCode.OK)
            } else {
                call.respondText("Erro ao deletar usuario", status = HttpStatusCode.BadRequest)
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