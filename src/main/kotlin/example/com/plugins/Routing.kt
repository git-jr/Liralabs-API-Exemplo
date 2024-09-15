package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    // user
    routing {
        get("/") {
            call.respondText("Api User funcionando")
        }

        get("/deploy") {
            call.respondText("Deploy na Render funcionando")
        }
    }

}