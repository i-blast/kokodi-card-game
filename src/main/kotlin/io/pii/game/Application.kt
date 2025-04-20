package io.pii.game

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.pii.game.modules.configureDatabase
import io.pii.game.modules.configureErrorHandling
import io.pii.game.modules.configureSecurity
import io.pii.game.modules.configureSerialization
import io.pii.game.user.UserRepository
import io.pii.game.user.UserService
import io.pii.game.user.authRoutes

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val appConfig = environment.config

    // Configure modules
    val db = configureDatabase(appConfig)
    configureSecurity(appConfig)
    configureSerialization()
    configureErrorHandling()
//    configureRouting()

    // DI
    val userRepository = UserRepository(db)
    val userService = UserService(userRepository)

    routing {
        authRoutes(userService)
    }

}
