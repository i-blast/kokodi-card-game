package io.pii.game.user

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(
    userService: UserService,
) {

    post("/api/auth/register") {
        val registerUserDto = call.receive<RegisterUserRequest>()
        call.respond(userService.register(registerUserDto))
    }

    post("/api/auth/login") {
        val authRequestDto = call.receive<AuthRequest>()
        call.respond(userService.authenticate(authRequestDto))
    }

    /*    authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()!!
                call.respond(
                    mapOf(
                        "user" to principal.payload.getClaim("login").asString()
                    )
                )
            }
        }*/
}
