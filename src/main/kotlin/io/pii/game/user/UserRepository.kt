package io.pii.game.user

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserRepository(private val database: Database) {

    suspend fun findByLogin(login: String): UserEntity? = dbQuery {
        UsersTable
            .selectAll()
            .where(UsersTable.login eq login)
            .map { it.toUser() }
            .singleOrNull()
    }

    suspend fun save(user: UserEntity): UserEntity = dbQuery {
        val userId = UsersTable.insertAndGetId {
            it[login] = user.login
            it[password] = user.password
            it[name] = user.name
        }
        user.copy(id = userId.value)
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}