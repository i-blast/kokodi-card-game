package io.pii.game

import io.ktor.server.application.*
import io.pii.game.modules.configureDatabase
import io.pii.game.user.UserRepository
import io.pii.game.user.UserService

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val appConfig = environment.config

    val db = configureDatabase(appConfig)

    val userRepository = UserRepository(db)

    val userService = UserService(userRepository)

//    configureSecurity()
//    configureSerialization()
//    configureRouting()

}
