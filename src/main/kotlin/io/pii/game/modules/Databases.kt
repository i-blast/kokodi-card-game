package io.pii.game.modules

import io.ktor.server.application.*
import io.ktor.server.config.*
import io.pii.game.user.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.io.File


fun Application.configureDatabase(config: ApplicationConfig): Database {

    val dbUrl = config.property("database.url").getString()
    val dbFile = File(dbUrl.removePrefix("jdbc:sqlite:"))
    val isNeedDbInit = !dbFile.exists()

    val connectedDatabase = Database.connect(
        url = dbUrl,
        driver = config.property("database.driver").getString(),
    )

    if (isNeedDbInit) {
        transaction {
            log.info("Creating SQLite schema (DB file not found)")
            SchemaUtils.create(
                UsersTable,
            )
        }
    }

    return connectedDatabase
}

private val log = LoggerFactory.getLogger("configureDatabase")
