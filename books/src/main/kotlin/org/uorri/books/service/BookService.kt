package org.uorri.books.service

import org.uorri.common.dto.BookDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookService {
    fun getBookDetails(bookId: Int): Mono<BookDto>
    fun getBooks(): Flux<BookDto>

}