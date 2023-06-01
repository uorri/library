package org.uorri.books

import java.math.BigDecimal

data class BookDetails(
    val title: String,
    val cost: BigDecimal,
    val pageCount: BigDecimal,
    val authorFirstName: String,
    val authorLastName: String,
    val authorLogin: String,
    val genres: String
)