package org.uorri.common.entity

import org.springframework.data.annotation.Id

data class Book(
    @Id
    val id: Long? = null,
    val title: String,
    val cost: Double,
    val pageCount: Int,
    val authorId: Long,
    val genres: Set<Genre>
)
