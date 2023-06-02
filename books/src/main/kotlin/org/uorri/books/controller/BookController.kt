package org.uorri.books.controller

import org.springframework.web.bind.annotation.*
import org.uorri.books.service.BookService
import org.uorri.common.dto.BookDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(
    val bookService: BookService
) {

    @GetMapping
    fun getAllBooks(): Flux<BookDto> {
        return bookService.getBooks()
    }

    @GetMapping("/{id}")
    fun getBookDetailsByIdJooq(@PathVariable id: Int): Mono<BookDto> {
        return bookService.getBookDetails(id)
    }



}