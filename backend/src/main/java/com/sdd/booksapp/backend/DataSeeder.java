package com.sdd.booksapp.backend;

import com.sdd.booksapp.backend.entity.Book;
import com.sdd.booksapp.backend.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            bookRepository.saveAll(List.of(
                    new Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 1954,
                            "https://covers.openlibrary.org/b/id/9251899-L.jpg",
                            "An epic high-fantasy novel written by English author and scholar J. R. R. Tolkien."),
                    new Book("1984", "George Orwell", "Science Fiction", 1949,
                            "https://covers.openlibrary.org/b/id/153254-L.jpg",
                            "A dystopian social science fiction novel and cautionary tale, warning of the dangers of totalitarianism."),
                    new Book("Pride and Prejudice", "Jane Austen", "Romance", 1813,
                            "https://covers.openlibrary.org/b/id/8259443-L.jpg",
                            "A romantic novel of manners written by Jane Austen in 1813."),
                    new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 1960,
                            "https://covers.openlibrary.org/b/id/8228691-L.jpg",
                            "A novel by the American author Harper Lee. It was published in 1960 and was instantly successful."),
                    new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 1925,
                            "https://covers.openlibrary.org/b/id/8225261-L.jpg",
                            "A 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island.")
            ));
            System.out.println("Data seeding completed.");
        }
    }
}
