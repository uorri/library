package org.uorri.common.dto

data class UserDto(
    val firstName: String,
    val lastName: String,
    val login: String,
    val roles: Set<UserRoleDto>
)
