package example.com.plugins

import example.com.dto.IngredienteRequest
import example.com.dto.UserRequest
import example.com.model.Dieta
import example.com.model.Gosto
import example.com.model.IngredienteDaReceita
import example.com.model.Receita
import example.com.repository.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val userRepository = UserRepository()
    val receitaRepository = ReceitaRepository()
    val ingredienteRepository = IngredienteRepository()
    val dietaRepository = DietaRepository()
    val gostoRepository = GostosRepository()
    val ingredienteDaReceitaRepository = IngredienteDaReceitaRepository()

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

    // receita
    routing {
        get("/receita") {
            call.respondText("API Receita funcionando")
        }

        // getAllReceitas
        get("/receitas") {
            try {
                val receitas = receitaRepository.getAllReceitas()
                call.respond(receitas)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar receitas: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveReceitas
        post("/receitas") {
            try {
                val request = call.receive<List<Receita>>()
                receitaRepository.saveReceitas(request)
                call.respondText("Receitas gravadas com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar receitas: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // ingrediente
    routing {
        get("/ingrediente") {
            call.respondText("API Ingrediente funcionando")
        }

        get("/ingredientes") {
            try {
                val ingredientes = ingredienteRepository.getUserIngredients()
                call.respond(ingredientes)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar ingredientes: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // saveAllIngredients
        post("/ingredientes") {
            try {
                val ingredientes = call.receive<List<IngredienteRequest>>().map { it.toIngrediente() }
                ingredienteRepository.saveAllIngredients(ingredientes)
                call.respondText("Ingredientes gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar ingredientes: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // Dieta
    routing {
        get("/dieta") {
            call.respondText("API Dieta funcionando")
        }

        // getUserPreferences
        get("/dietas/{idUser}/{data}") {
            try {
                val idUser = call.parameters["idUser"]?.toIntOrNull()
                val data = call.parameters["data"]?.toIntOrNull()
                if (idUser == null || data == null) {
                    call.respond(HttpStatusCode.BadRequest, "Usuário ou data inválidos")
                    return@get
                }
                val preferencias = dietaRepository.getUserPreferences(idUser, data)
                call.respond(preferencias)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar preferências de dieta: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // getUserDiet
        get("/dietas/{idUser}") {
            try {
                val idUser = call.parameters["idUser"]?.toIntOrNull()
                if (idUser == null) {
                    call.respond(HttpStatusCode.BadRequest, "Usuário inválido")
                    return@get
                }
                val dietas = dietaRepository.getUserDiet(idUser)
                call.respond(dietas)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar dietas: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // addDiet
        post("/dietas") {
            try {
                val request = call.receive<List<Dieta>>()
                dietaRepository.addDiet(request)
                call.respondText("Dietas gravadas com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar dietas: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // Gosto
    routing {
        get("/gostos") {
            call.respondText("API Gosto funcionando")
        }

        // getUserPreferences
        get("/gostos/{idGosto}") {
            try {
                val idGosto = call.parameters["idGosto"]?.toIntOrNull()
                if (idGosto == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@get
                }
                val gostos = gostoRepository.getUserPreferences(idGosto)
                call.respond(gostos)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar gostos: $e", status = HttpStatusCode.BadRequest)
            }
        }

        // setUserPreference
        post("/gostos") {
            try {
                val request = call.receive<List<Gosto>>()
                gostoRepository.setUserPreference(request)
                call.respondText("Gostos gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar gostos: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }

    // IngredienteDaReceitaDao
    routing {
        get("/ingredienteDaReceita") {
            call.respondText("API IngredienteDaReceita funcionando")
        }

        // addIngredienteToReceita
        post("/ingredienteDaReceita") {
            try {
                val request = call.receive<IngredienteDaReceita>()
                ingredienteDaReceitaRepository.addIngredienteToReceita(request)
                call.respondText("Ingrediente da receita gravados com sucesso", status = HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respondText("Erro ao gravar ingrediente da receita: $e", status = HttpStatusCode.BadRequest)
            }
        }
    }
}