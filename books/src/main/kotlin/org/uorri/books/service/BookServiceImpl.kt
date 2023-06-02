package org.uorri.books.service

import com.uorri.common.db.Tables
import org.jooq.DSLContext
import org.jooq.Record7
import org.jooq.impl.DSL
import org.springframework.stereotype.Service
import org.uorri.common.dto.BookDto
import org.uorri.common.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookServiceImpl(
    val r2dbcDSLContext: DSLContext
) : BookService {

    val b = Tables.BOOKS
    val bg = Tables.BOOKS_GENRES
    val g = Tables.GENRES
    val u = Tables.USERS

    override fun getBooks(): Flux<BookDto> {
        val query = r2dbcDSLContext
            .select(
                b.TITLE,
                b.COST,
                b.PAGE_COUNT,
                u.FIRST_NAME,
                u.LAST_NAME,
                u.LOGIN,
                DSL.arrayAgg(g.NAME).`as`("genres")
            )
            .from(b)
            .join(u).on(b.AUTHOR_ID.eq(u.ID))
            .join(bg).on(b.ID.eq(bg.BOOK_ID))
            .join(g).on(bg.GENRE_ID.eq(g.ID))
            .groupBy(
                b.TITLE,
                b.COST,
                b.PAGE_COUNT,
                u.FIRST_NAME,
                u.LAST_NAME,
                u.LOGIN
            )

        return Flux.from(query).map { it.toBookDto() }
    }

    override fun getBookDetails(bookId: Int): Mono<BookDto> {
        val query = r2dbcDSLContext
            .select(
                b.TITLE,
                b.COST,
                b.PAGE_COUNT,
                u.FIRST_NAME,
                u.LAST_NAME,
                u.LOGIN,
                DSL.arrayAgg(g.NAME).`as`("genres")
            )
            .from(b)
            .join(u).on(b.AUTHOR_ID.eq(u.ID))
            .join(bg).on(b.ID.eq(bg.BOOK_ID))
            .join(g).on(bg.GENRE_ID.eq(g.ID))
            .where(b.ID.eq(bookId))
            .groupBy(
                b.TITLE,
                b.COST,
                b.PAGE_COUNT,
                u.FIRST_NAME,
                u.LAST_NAME,
                u.LOGIN
            )

        return Mono.from(query).map { it.toBookDto() }
    }

    private fun Record7<String, Double, Int, String, String, String, Array<String>>.toBookDto() =
        BookDto(
            title = get(b.TITLE),
            cost = get(b.COST),
            pageCount = get(b.PAGE_COUNT),
            author = UserDto(
                firstName = get(u.FIRST_NAME),
                lastName = get(u.LAST_NAME),
                login = get(u.LOGIN)
            ),
            genres = (get("genres") as Array<String>).toSet()
        )


}