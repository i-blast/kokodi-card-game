package io.pii.game.user

class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun findByLogin(login: String) = userRepository.findByLogin(login)

    suspend fun register(user: UserCreateDto) = userRepository.save(user.toEntity())
}
