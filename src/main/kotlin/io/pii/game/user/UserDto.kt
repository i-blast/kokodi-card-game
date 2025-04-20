package io.pii.game.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class RegisterUserRequest(
    val login: String,
    val password: String,
    val name: String,
)

@Serializable
data class UserResponse(
    @Contextual val id: UUID?,
    val login: String,
    val name: String,
)

fun UserEntity.toDto(): UserResponse = UserResponse(
    id = this.id,
    login = this.login,
    name = this.name,
)

@Serializable
data class AuthRequest(
    val login: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val token: String
)

