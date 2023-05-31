package org.uorri.books.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.uorri.books.BookDetails
import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long> {

    fun getBooksByAuthorId(authorId: Long): Flux<Book>

            @Query("SELECT b.id, b.title, b.cost, b.page_count, " +
                    "u.id, u.first_name, u.last_name, u.login, u.password, " +
                    "r.id as role_id, g.id, g.name " +
                    "FROM books b " +
                    "JOIN users u ON b.author_id = u.id " +
                    "JOIN roles r ON u.role_id = r.id " +
                    "JOIN books_genres bg ON b.id = bg.book_id " +
                    "JOIN genres g ON bg.genre_id = g.id " +
                    "WHERE b.id = :bookId")
            fun findBookDetailsById(bookId: Long): Mono<BookDetails>

}