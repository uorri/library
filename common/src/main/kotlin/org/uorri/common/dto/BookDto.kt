package org.uorri.common.dto


data class BookDto(
    val title: String,
    val cost: Double,
    val pageCount: Int,
    val author: UserDto,
    val genres: Set<String>
)