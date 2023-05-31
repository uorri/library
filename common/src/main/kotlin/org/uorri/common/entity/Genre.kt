package org.uorri.common.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("genres")

data class Genre(
    @Id
    val id: Long? = null,
    val name: String
)
