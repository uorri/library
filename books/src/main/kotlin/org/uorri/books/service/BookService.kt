package org.uorri.books.service

import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookService {

    fun getAllBooks(): Flux<Book>
    fun createBook(book: Mono<Book>): Mono<Book>
    fun removeBook(book: Mono<Book>): Mono<Void>
    fun getBooksByCostIn(range: IntRange): Flux<Book>

}