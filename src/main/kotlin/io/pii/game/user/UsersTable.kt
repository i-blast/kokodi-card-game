package io.pii.game.user

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

object UsersTable : UUIDTable("users") {

    val login = varchar("login", 50).uniqueIndex()

    val password = varchar("password", 64)

    val name = varchar("name", 50)
}

data class UserEntity(
    val id: UUID?,
    val login: String,
    val password: String,
    val name: String
)

fun ResultRow.toUser(): UserEntity = UserEntity(
    id = this[UsersTable.id].value,
    login = this[UsersTable.login],
    password = this[UsersTable.password],
    name = this[UsersTable.name]
)
