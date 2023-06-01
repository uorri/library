package org.uorri.books.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.uorri.books.BookDetails
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
    fun getAllBooks(): Flux<BookDetails> {
        return bookService.getAllBooks()
    }

    @PostMapping
    fun createBooks(book: Mono<Book>): Mono<ResponseEntity<Book>> {
        return bookService.createBook(book).map { ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun getBookDetailsById(@PathVariable id: Long) : Mono<BookDetails>{
        return bookService.getBookById(id)
    }


}