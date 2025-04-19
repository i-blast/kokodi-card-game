package io.pii.game.user

data class UserCreateDto(
    val login: String,
    val password: String,
    val name: String
)

fun UserCreateDto.toEntity(): UserEntity = UserEntity(
    id = null,
    login = login,
    password = password,
    name = name
)
