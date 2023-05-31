package org.uorri.common.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    @Column("first_name")
    val firstName: String,
    @Column("last_name")
    val lastName: String,
    val login: String,
    val password: String,
    @Column("role_id")
    val roleId: Int
)
