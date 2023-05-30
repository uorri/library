package org.uorri.common.entity

import org.springframework.data.annotation.Id


data class Genre(
    @Id
    val id: Long? = null,
    val name: String
)
