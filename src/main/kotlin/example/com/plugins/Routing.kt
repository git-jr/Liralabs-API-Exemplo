package example.com.plugins

import example.com.dto.MarketItemRequest
import example.com.dto.UserRequest
import example.com.dto.toMarketItemResponse
import example.com.dto.toUserResponse
import example.com.repository.MarketItemRepository
import example.com.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val marketItemRepository = MarketItemRepository()

    // user
    routing {
        get("/") {
            call.respondText("Api funcionando")
        }

        get("/users") {
            val response = userRepository.users().map {
                it.toUserResponse()
            }
            call.respond(response)
        }

        post("/users") {
            try {
                val request = call.receive<UserRequest>()
                userRepository.save(request.toUser())?.let {
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
}