package org.uorri.books.service

import org.springframework.stereotype.Service
import org.uorri.books.repository.BookRepository
import org.uorri.common.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookServiceImpl(val bookRepository: BookRepository) : BookService{
    override fun getAllBooks(): Flux<Book> {
        return bookRepository.findAll()
    }

    override fun createBook(book: Mono<Book>): Mono<Book> {
        return book.flatMap { bookRepository.save(it) }
    }

    override fun removeBook(book: Mono<Book>): Mono<Void> {
        return book.flatMap { bookRepository.deleteById(it.id) }
    }

    override fun getBooksByCostIn(range: IntRange): Flux<Book> {
        return bookRepository.getBooksByCostIsIn(range)
    }
}