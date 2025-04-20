package io.pii.game.user

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.ktor.server.config.*
import io.pii.game.BaseServiceTest
import io.pii.game.security.JwtConfig
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Table
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class UserServiceTest : BaseServiceTest() {

    override val tables: Array<Table> = arrayOf(UsersTable)

    private lateinit var userService: UserService

    @BeforeTest
    fun setupService() {
        val userRepository = UserRepository(testDatabase)
        userService = UserService(userRepository)
    }

    @BeforeTest
    fun setupJwt() {
        val config = MapApplicationConfig(
            "ktor.security.jwt.secret" to "test-secret",
            "ktor.security.jwt.issuer" to "test-issuer",
            "ktor.security.jwt.audience" to "test-audience"
        )
        JwtConfig.init(config)
    }

    @Test
    fun `save should store user and return it successfully`(): Unit = runBlocking {
        val createDto = RegisterUserRequest("test_user", "testpass", "Test User")
        val savedUser = userService.register(createDto)
        savedUser.id.shouldNotBeNull()
        savedUser.login shouldBe createDto.login
    }

    @Test
    fun `authenticate should return user on correct password`(): Unit = runBlocking {
        val request = RegisterUserRequest("auth_user", "securepass", "Auth Test")
        val registered = userService.register(request)

        val authResult = userService.authenticate(AuthRequest("auth_user", "securepass"))
        authResult.shouldNotBeNull()

        val decoded = JwtConfig.verifier.verify(authResult.token)
        decoded.getClaim("login").asString() shouldBe "auth_user"
        decoded.getClaim("id").asString() shouldBe registered.id.toString()
        decoded.issuer shouldBe "test-issuer"
        decoded.audience shouldContain "test-audience"
    }

    @Test
    fun `authenticate should return null on wrong password`(): Unit = runBlocking {
        val request = RegisterUserRequest("fail_user", "goodpass", "Wrong Pw")
        userService.register(request)
        shouldThrowExactly<InvalidCredentialsException> {
            userService.authenticate(AuthRequest("fail_user", "badpass"))
        }
    }
}
