package io.pii.game.user

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.pii.game.BaseServiceTest
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Table
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory

class UserServiceTest : BaseServiceTest() {

    override val tables: Array<Table> = arrayOf(UsersTable)

    private lateinit var userService: UserService

    companion object {
        private val log = LoggerFactory.getLogger(UserServiceTest::class.java)
    }

    @BeforeEach
    fun setupService() {
        val userRepository = UserRepository(testDatabase)
        userService = UserService(userRepository)
    }

    @Test
    fun `save should store user and return it successfully`(): Unit = runBlocking {
        val createDto = UserCreateDto("test_user", "testpass", "Test User")
        val savedUser = userService.register(createDto)
        savedUser.id.shouldNotBeNull()
        savedUser.login shouldBe createDto.login
    }
}
