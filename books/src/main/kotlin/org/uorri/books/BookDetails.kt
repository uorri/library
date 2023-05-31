package org.uorri.books

import org.uorri.common.entity.Genre
import org.uorri.common.entity.User

data class BookDetails(
    val id: Long,
    val title: String,
    val cost: Float,
    val pageCount: Int,
    val author: User,
    val genres: Set<Genre>
)