package org.uorri.books.service

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import org.uorri.books.BookDetails
import org.uorri.books.mapper.BookMapper
import org.uorri.books.repository.BookRepository
import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookServiceImpl(
    val bookRepository: BookRepository,
    val bookMapper: BookMapper,
    val client: DatabaseClient
) : BookService {

    override fun getAllBooks(): Flux<BookDetails> {
        val query = baseQuery.plus(
            "GROUP BY b.title, b.cost, b.page_count, u.first_name, u.last_name, u.login;"
        )
        return client.sql(query).map(bookMapper::apply).all()
    }

    override fun createBook(book: Mono<Book>): Mono<Book> {
        return book.flatMap { bookRepository.save(it) }
    }

    override fun removeBook(book: Mono<Book>): Mono<Void> {
        return book.flatMap { bookRepository.deleteById(it.id) }
    }

    override fun getBookById(id: Long): Mono<BookDetails> {
        val query = baseQuery.plus(
            """
                WHERE b.id = :id
                GROUP BY b.title, b.cost, b.page_count, u.first_name, u.last_name, u.login;
                """
        )
        return client.sql(query).bind("id", id).map(bookMapper::apply).one()
    }

    /*fun getBookDetails(bookId: Long): BookDetails {
        val b = Tables.BOOKS
        val bg = Tables.BOOKS_GENRES
        val g = Tables.GENRES
        val u = Tables.USERS

        val result = r2dbcDSLContext
            .select(
                b.TITLE,
                b.COST,
                b.PAGE_COUNT,
                u.FIRST_NAME,
                u.LAST_NAME,
                u.LOGIN,
                g.NAME
            )
            .from(b)
            .join(u).on(b.AUTHOR_ID.eq(u.ID))
            .join(bg).on(b.ID.eq(bg.BOOK_ID))
            .join(g).on(bg.GENRE_ID.eq(g.ID))
            .where(b.ID.eq(bookId))
            .fetchGroups(
                { it.into(b) },
                { it.into(g.NAME) }
            )

        val bookRecord = result.keys.first()
        val genreRecords = result[bookRecord]

        val author = User(
            bookRecord.get(u.FIRST_NAME),
            bookRecord.get(u.LAST_NAME),
            bookRecord.get(u.LOGIN)
        )

        val genres = genreRecords?.map { GenreDto(it) }?.toSet()

        return BookDetails(
            bookRecord.get(b.TITLE),
            bookRecord.get(b.COST).toFloat(),
            bookRecord.get(b.PAGE_COUNT),
            author,
            genres
        )
    }*/

    val baseQuery = """
            SELECT b.title, b.cost::numeric, b.page_count::numeric, 
            u.first_name, u.last_name, u.login, 
            string_agg(g.name, ', ') AS genres
            FROM books b
             JOIN books_genres bg ON b.id = bg.book_id
             JOIN genres g ON bg.genre_id = g.id
             JOIN users u ON b.author_id = u.id
            """


}