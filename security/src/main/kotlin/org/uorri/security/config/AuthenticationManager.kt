package org.uorri.security.config

import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class AuthenticationManager(
    @Autowired val jwtService: JwtService
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val jwtToken: String = authentication.credentials.toString()
        return Mono.just(getUserAuthorities(jwtToken)).map(::getAuthorities)
    }

    private fun getAuthorities(userAuthorities: UserAuthorities): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(
            userAuthorities.username,
            null,
            userAuthorities.authorities.map { role: String -> SimpleGrantedAuthority(role) }
        )
    }

    fun getUserAuthorities(token: String): UserAuthorities {
        val claims: Claims = jwtService.getClaims(token)
        return UserAuthorities(claims.subject, listOf(claims["roles"].toString()))
    }
}