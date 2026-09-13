package com.sdd.booksapp.backend;

import com.sdd.booksapp.backend.entity.Book;
import com.sdd.booksapp.backend.repository.BookRepository;
import com.sdd.booksapp.backend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks_NoSortNoFilter() {
        List<Book> mockBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll(Sort.unsorted())).thenReturn(mockBooks);

        List<Book> result = bookService.getAllBooks(null, null);

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll(Sort.unsorted());
    }

    @Test
    void testGetAllBooks_WithFilter() {
        Book book1 = new Book("Title 1", "Auth 1", "Sci-Fi", 2000, "", "");
        when(bookRepository.findByCategory("Sci-Fi")).thenReturn(Arrays.asList(book1));

        List<Book> result = bookService.getAllBooks("Sci-Fi", null);

        assertEquals(1, result.size());
        assertEquals("Sci-Fi", result.get(0).getCategory());
        verify(bookRepository, times(1)).findByCategory("Sci-Fi");
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testCreateBook() {
        Book book = new Book("Title", "Author", "Cat", 2023, "", "");
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);

        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBook_Found() {
        Book existing = new Book("Old", "Old", "Old", 2000, "", "");
        existing.setId(1L);
        Book updated = new Book("New", "New", "New", 2023, "", "");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArguments()[0]);

        Book result = bookService.updateBook(1L, updated);

        assertEquals("New", result.getTitle());
        assertEquals(2023, result.getPublicationYear());
    }

    @Test
    void testUpdateBook_NotFound() {
        Book updated = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, updated));
    }
}
