package com.sdd.booksapp.backend;

import com.sdd.booksapp.backend.entity.Book;
import com.sdd.booksapp.backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindByCategory() {
        Book b1 = new Book("B1", "A1", "Fantasy", 2000, "", "");
        Book b2 = new Book("B2", "A2", "Sci-Fi", 2000, "", "");
        bookRepository.save(b1);
        bookRepository.save(b2);

        List<Book> fantasyBooks = bookRepository.findByCategory("Fantasy");
        assertEquals(1, fantasyBooks.size());
        assertEquals("B1", fantasyBooks.get(0).getTitle());
    }
}
