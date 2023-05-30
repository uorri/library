package org.uorri.security.config

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
