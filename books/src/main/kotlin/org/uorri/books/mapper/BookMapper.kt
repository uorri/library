package org.uorri.books.mapper

import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import org.springframework.stereotype.Component
import org.uorri.books.BookDetails
import org.uorri.common.entity.Book
import java.math.BigDecimal
import java.util.function.BiFunction


@Component
class BookMapper : BiFunction<Row, RowMetadata, BookDetails> {

    override fun apply(row: Row, rowMetadata: RowMetadata): BookDetails {
        val title: String = row["title", String::class.java]
        val cost: BigDecimal = row["cost", BigDecimal::class.java]
        val pageCount: BigDecimal = row["page_count", BigDecimal::class.java]
        val authorFirstName: String = row["first_name", String::class.java]
        val authorLastName: String = row["last_name", String::class.java]
        val authorLogin: String = row["login", String::class.java]
        val genres: String = row["genres", String::class.java]
        return BookDetails(
            title = title,
            cost = cost,
            pageCount = pageCount,
            authorFirstName = authorFirstName,
            authorLastName = authorLastName,
            authorLogin = authorLogin,
            genres = genres
        )
    }
}
