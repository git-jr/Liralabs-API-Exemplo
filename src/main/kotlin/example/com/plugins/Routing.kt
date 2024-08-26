package example.com.plugins

import example.com.dto.PetRequest
import example.com.dto.PetshopRequest
import example.com.dto.UserRequest
import example.com.dto.toUserResponse
import example.com.repository.PetRepository
import example.com.repository.PetshopRepository
import example.com.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val petRepository = PetRepository()
    val petshopRepository = PetshopRepository()

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

        // getUserByEmail
        get("/users/{email}") {
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
                call.respond(user.toUserResponse())
            } catch (e: Exception) {
                call.respondText("Erro ao buscar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveUser
        post("/users") {
            try {
                val request = call.receive<UserRequest>()
                val user = request.toUser()
                val savedUser = userRepository.saveUser(user)
                call.respondText("Usuário gravado", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveAllUsers
        post("/users/batch") {
            try {
                val users = call.receive<List<UserRequest>>().map { it.toUser() }
                userRepository.saveAllUsers(users)
                call.respondText("Usuários gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar usuários: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // updateUserPassword
        patch("/users/{email}/password") {
            try {
                val email = call.parameters["email"]
                val newPassword = call.request.queryParameters["newPassword"]
                if (email == null || newPassword == null) {
                    call.respond(HttpStatusCode.BadRequest, "Email ou senha inválidos")
                    return@patch
                }
                if (userRepository.updateUserPassword(email, newPassword)) {
                    call.respondText("Senha atualizada com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao atualizar senha", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao atualizar senha: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // deleteUser
        delete("/users/{email}") {
            try {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest, "Email inválido")
                    return@delete
                }
                if (userRepository.deleteUser(email)) {
                    call.respondText("Usuário deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao deletar usuário", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao deletar usuário: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // pet
    routing {
        get("/") {
            call.respondText("API Pet funcionando")
        }

        // getAllPets
        get("/pets") {
            try {
                val pets = petRepository.getAllPets()
                call.respond(pets)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar pets: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getPetById
        get("/pets/{id}") {
            try {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@get
                }
                val pet = petRepository.getPetById(id)
                if (pet == null) {
                    call.respond(HttpStatusCode.NotFound, "Pet não encontrado")
                    return@get
                }
                call.respond(pet)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar pet: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // savePet
        post("/pets") {
            try {
                val request = call.receive<PetRequest>()
                val pet = request.toPet()
                val savedPet = petRepository.savePet(pet)
                call.respondText("Pet gravado com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar pet: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveAllPets
        post("/pets/batch") {
            try {
                val pets = call.receive<List<PetRequest>>().map { it.toPet() }
                petRepository.saveAllPets(pets)
                call.respondText("Pets gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar pets: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // deletePet
        delete("/pets/{id}") {
            try {
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                if (petRepository.deletePet(id)) {
                    call.respondText("Pet deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao deletar pet", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao deletar pet: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // petshop
    routing {
        get("/") {
            call.respondText("API Petshop funcionando")
        }

        // getAllPetshops
        get("/petshops") {
            try {
                val petshops = petshopRepository.getAllPetshops()
                call.respond(petshops)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar petshops: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getPetshopByCnpj
        get("/petshops/{cnpj}") {
            try {
                val cnpj = call.parameters["cnpj"]
                if (cnpj == null) {
                    call.respond(HttpStatusCode.BadRequest, "CNPJ inválido")
                    return@get
                }
                val petshop = petshopRepository.getPetshopByCnpj(cnpj)
                if (petshop == null) {
                    call.respond(HttpStatusCode.NotFound, "Petshop não encontrado")
                    return@get
                }
                call.respond(petshop)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar petshop: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // savePetshop
        post("/petshops") {
            try {
                val request = call.receive<PetshopRequest>()
                val petshop = request.toPetshop()
                val savedPetshop = petshopRepository.savePetshop(petshop)
                call.respondText("Petshop gravado com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar petshop: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveAllPetshops
        post("/petshops/batch") {
            try {
                val petshops = call.receive<List<PetshopRequest>>().map { it.toPetshop() }
                petshopRepository.saveAllPetshops(petshops)
                call.respondText("Petshops gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar petshops: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // deletePetshop
        delete("/petshops/{uid}") {
            try {
                val uid = call.parameters["uid"]?.toIntOrNull()
                if (uid == null) {
                    call.respond(HttpStatusCode.BadRequest, "UID inválido")
                    return@delete
                }
                if (petshopRepository.deletePetshop(uid)) {
                    call.respondText("Petshop deletado com sucesso", status = HttpStatusCode.OK)
                } else {
                    call.respondText("Erro ao deletar petshop", status = HttpStatusCode.BadRequest)
                }
            } catch (e: Exception) {
                call.respondText("Erro ao deletar petshop: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

}