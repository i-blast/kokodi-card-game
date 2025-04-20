package io.pii.game.user

import io.pii.game.security.JwtConfig
import org.mindrot.jbcrypt.BCrypt

class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun findByLogin(login: String) = userRepository.findByLogin(login)

    suspend fun register(user: RegisterUserRequest): UserResponse {
        val newEntity = user.toEntity()
        return userRepository.save(newEntity).toDto()
    }

    suspend fun authenticate(dto: AuthRequest): AuthResponse {

        val user = userRepository.findByLogin(dto.login)
            ?: throw UserNotFoundException(dto.login)

        if (!BCrypt.checkpw(dto.password, user.password)) {
            throw InvalidCredentialsException()
        }

        val token = JwtConfig.generateToken(user)
        return AuthResponse(token)
    }

    fun RegisterUserRequest.toEntity(): UserEntity = UserEntity(
        id = null,
        login = login,
        password = BCrypt.hashpw(password, BCrypt.gensalt()),
        name = name
    )
}
