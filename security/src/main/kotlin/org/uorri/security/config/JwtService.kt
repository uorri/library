package org.uorri.security.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.uorri.common.dto.LoginCredentials
import org.uorri.common.entity.User
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets
import java.time.Duration.ofMinutes
import java.time.Instant.now
import java.util.*
import javax.crypto.SecretKey


@Service
class JwtService(
    val passwordEncoder: PasswordEncoder
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(
        "ThisIsMySecretKey123!@#hmcacSnaKeyThisIs!2234".toByteArray(StandardCharsets.UTF_8)
    )
    private val parser: JwtParser = Jwts.parserBuilder().setSigningKey(key).build()

    fun getAuthResponseByUser(user: User): AuthResponse {
        val accessToken: String = generateToken(user)
        return AuthResponse(accessToken)
    }

    fun getAuthResponse(userMono: Mono<User>): Mono<AuthResponse> {
        return userMono.flatMap { Mono.just(getAuthResponseByUser(it)) }
    }

    fun getAuthResponse(loginCredentials: LoginCredentials, userMono: Mono<User>): Mono<AuthResponse> {
        return userMono.flatMap { user ->
            val passwordMatches = passwordEncoder.matches(loginCredentials.password, user.password)
            if (passwordMatches) {
                Mono.just(getAuthResponseByUser(user))
            } else {
                Mono.error(IllegalArgumentException("Invalid credentials"))
            }
        }
    }

    private fun generateToken(user: User): String {
        val expiration = now().plus(ofMinutes(ACCESS_TOKEN_EXPIRATION_MINUTES))
        return generateToken(user, Date.from(expiration))
    }

    fun getClaims(token: String): Claims {
        return parser.parseClaimsJws(token.replace("Bearer ", "")).body
    }

    fun getRoleId(token: String): Int {
        val roleId = getClaims(token)["roles"]
        return if (roleId is Int) roleId else 0
    }

    fun getLoginByToken(token: String): String {
        return getClaims(token).subject
    }

    private fun generateToken(user: User, expiration: Date): String {
        return Jwts.builder()
            .setSubject(user.login)
            .claim("roles", user.roleId)
            .setIssuedAt(Date.from(now()))
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    companion object {
        private const val ACCESS_TOKEN_EXPIRATION_MINUTES = 15L
        private const val REFRESH_TOKEN_EXPIRATION_MINUTES = 30L
    }

}