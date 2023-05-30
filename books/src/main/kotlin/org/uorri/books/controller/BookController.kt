package org.uorri.books.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.uorri.books.service.BookService
import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(
    val bookService: BookService
) {

    @GetMapping()
    fun getAllBooks(): Flux<ResponseEntity<Book>> {
        return bookService.getAllBooks().map { ResponseEntity.ok(it) }
    }

    @PostMapping
    fun createBooks(book: Mono<Book>): Mono<ResponseEntity<Book>> {
        return bookService.createBook(book).map { ResponseEntity.ok(it) }
    }

    @GetMapping
    fun getBooksByCostIn(book: Mono<Book>): Mono<ResponseEntity<Book>> {
        return bookService.createBook(book).map { ResponseEntity.ok(it) }
    }

    @PostMapping
    fun removeBook(book: Mono<Book>): Mono<Void> {
        return bookService.removeBook(book)
    }


}