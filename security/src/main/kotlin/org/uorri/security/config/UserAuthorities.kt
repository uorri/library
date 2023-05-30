package org.uorri.security.config

data class UserAuthorities(
    val username: String,
    val authorities: List<String>
)
