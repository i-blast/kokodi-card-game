package io.pii.game.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.pii.game.security.JwtConfig


fun Application.configureSecurity(config: ApplicationConfig) {

    JwtConfig.init(config)

    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.verifier)
            validate { credential ->
                val login = credential.payload.getClaim("login").asString()
                if (login != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
