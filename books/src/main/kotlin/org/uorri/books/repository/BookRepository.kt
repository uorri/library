package org.uorri.books.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long> {

    fun getBooksByAuthorId(authorId: Long): Flux<Book>

    fun getBooksByCostIsIn(range: IntRange) : Flux<Book>

}