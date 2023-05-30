package org.uorri.common.entity

import org.springframework.data.annotation.Id

data class User(
    @Id
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val login: String,
    val password: String,
    val roleId: Int
)
