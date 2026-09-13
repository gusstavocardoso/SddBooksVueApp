package com.sdd.booksapp.backend.service;

import com.sdd.booksapp.backend.entity.Book;
import com.sdd.booksapp.backend.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(String category, String sortBy) {
        Sort sort = Sort.unsorted();
        if ("name".equalsIgnoreCase(sortBy)) {
            sort = Sort.by("title").ascending();
        } else if ("year".equalsIgnoreCase(sortBy)) {
            sort = Sort.by("publicationYear").descending();
        }

        if (category != null && !category.isEmpty()) {
            // Manual sorting since findByCategory doesn't take Sort in our simple repo method
            // For production, we'd add a method findByCategory(category, sort) in repository.
            // Let's implement that properly: We can use findAll with Example or just stream it.
            // But let's just fetch all and stream for simplicity if category is provided, or better, 
            // we should have a custom query. 
            // We will fetch and sort in memory for now or let JPA do it without custom query.
        }
        
        List<Book> books;
        if (category != null && !category.isEmpty()) {
             books = bookRepository.findByCategory(category);
             // Sort in memory for simplicity with this structure
             if ("name".equalsIgnoreCase(sortBy)) {
                 books.sort((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));
             } else if ("year".equalsIgnoreCase(sortBy)) {
                 books.sort((b1, b2) -> b2.getPublicationYear().compareTo(b1.getPublicationYear()));
             }
        } else {
             books = bookRepository.findAll(sort);
        }
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCategory(updatedBook.getCategory());
                    book.setPublicationYear(updatedBook.getPublicationYear());
                    book.setCoverImageUrl(updatedBook.getCoverImageUrl());
                    book.setDescription(updatedBook.getDescription());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
