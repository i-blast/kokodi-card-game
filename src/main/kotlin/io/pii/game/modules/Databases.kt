package io.pii.game.modules

import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database


fun Application.configureDatabase(config: ApplicationConfig) =
    Database.connect(
        url = config.property("database.url").getString(),
        driver = config.property("database.driver").getString(),
    )
