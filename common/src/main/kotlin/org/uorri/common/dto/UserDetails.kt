package org.uorri.common.dto

data class UserDetails(
    val firstName: String,
    val lastName: String,
    val login: String,
    val password: String,
)