package org.uorri.books.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.uorri.common.entity.Book

@Repository
interface BookRepository : ReactiveCrudRepository<Book, Long>