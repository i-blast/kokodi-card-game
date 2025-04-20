package io.pii.game.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import io.pii.game.user.UserEntity
import java.util.*

object JwtConfig {

    private const val VALIDITY = 36_000_00 * 24

    private lateinit var algorithm: Algorithm
    private lateinit var verifierInternal: JWTVerifier
    private lateinit var issuer: String
    private lateinit var audience: String

    val verifier: JWTVerifier
        get() = verifierInternal

    fun init(config: ApplicationConfig) {
        val jwtConfig = config.config("ktor.security.jwt")

        issuer = jwtConfig.property("issuer").getString()
        audience = jwtConfig.property("audience").getString()

        algorithm = Algorithm.HMAC256(jwtConfig.property("secret").getString())
        verifierInternal = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
    }

    fun generateToken(user: UserEntity): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("id", user.id.toString())
        .withClaim("login", user.login)
        .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY))
        .sign(algorithm)
}
