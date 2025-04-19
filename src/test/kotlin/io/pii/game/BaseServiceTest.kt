package io.pii.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.slf4j.LoggerFactory
import java.nio.file.Files

abstract class BaseServiceTest {

    abstract val tables: Array<Table>

    companion object {
        private val log = LoggerFactory.getLogger(BaseServiceTest::class.java)

        lateinit var testDatabase: Database

        @JvmStatic
        @BeforeAll
        fun setup() {
            testDatabase = Database.connect(
//                url = "jdbc:sqlite:file:testdb?mode=memory&cache=shared",
                url = "jdbc:sqlite:${Files.createTempFile("testdb", ".sqlite").toAbsolutePath()}",
                driver = "org.sqlite.JDBC"
            )
        }
    }

    @BeforeEach
    fun initSchema() = runBlocking {
        newSuspendedTransaction(Dispatchers.IO, testDatabase) {
            SchemaUtils.drop(*tables)
            SchemaUtils.create(*tables)
            log.info("Created tables: ${tables.joinToString { it.tableName }}")
        }
    }
}
