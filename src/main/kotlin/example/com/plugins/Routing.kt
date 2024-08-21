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
    val userRepository = UserRepository()

    // user
    routing {
        get("/") {
            call.respondText("API User funcionando")
        }

        // getAllUsers
        get("/users") {
            try {
                val users = userRepository.getAllUsers()
                call.respond(users)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuários: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getUserById
        get("/users/{id}") {
            try {
                val idUser = call.parameters["id"]?.toIntOrNull()
                if (idUser == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@get
                }
                val user = userRepository.getUserById(idUser)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "Usuário não encontrado")
                    return@get
                }
                call.respond(user)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getUserByEmail
        get("/users/email/{email}") {
            try {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest, "Email inválido")
                    return@get
                }
                val user = userRepository.getUserByEmail(email)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "Usuário não encontrado")
                    return@get
                }
                call.respond(user)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getUserByEmailAndPassword
        get("/users/login") {
            try {
                val email = call.request.queryParameters["email"]
                val password = call.request.queryParameters["password"]
                if (email == null || password == null) {
                    call.respond(HttpStatusCode.BadRequest, "Email ou senha inválidos")
                    return@get
                }
                val user = userRepository.getUserByEmailAndPassword(email, password)
                if (user == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Credenciais inválidas")
                    return@get
                }
                call.respond(user)
            } catch (e: Exception) {
                call.respondText("Erro ao autenticar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // insertUser
        post("/users") {
            try {
                val request = call.receive<UserRequest>()
                val user = request.toUser()
                val savedUser = userRepository.saveUser(user)
                call.respondText("Usuário gravado com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updateUser
        patch("/users/{id}") {
            try {
                val idUser = call.parameters["id"]?.toIntOrNull()
                if (idUser == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@patch
                }
                val request = call.receive<UserRequest>()
                val user = request.toUser().copy(idUser = idUser)
                if (userRepository.updateUser(user)) {
                    call.respondText("Usuário atualizado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar usuário", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updateUserImage
        patch("/users/{id}/image") {
            try {
                val idUser = call.parameters["id"]?.toIntOrNull()
                val image = call.request.queryParameters["image"]
                if (idUser == null || image == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID ou imagem inválidos")
                    return@patch
                }
                if (userRepository.updateUserImage(idUser, image)) {
                    call.respondText("Imagem do usuário atualizada com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar imagem do usuário", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar imagem do usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updateUserPassword
        patch("/users/{id}/password") {
            try {
                val idUser = call.parameters["id"]?.toIntOrNull()
                val password = call.request.queryParameters["password"]
                if (idUser == null || password == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID ou senha inválidos")
                    return@patch
                }
                if (userRepository.updateUserPassword(idUser, password)) {
                    call.respondText("Senha do usuário atualizada com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar senha do usuário", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar senha do usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // deleteUser
        delete("/users/{id}") {
            try {
                val idUser = call.parameters["id"]?.toIntOrNull()
                if (idUser == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                if (userRepository.deleteUser(idUser)) {
                    call.respondText("Usuário deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao deletar usuário", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao deletar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

}