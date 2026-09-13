package com.sdd.booksapp.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdd.booksapp.backend.controller.BookController;
import com.sdd.booksapp.backend.entity.Book;
import com.sdd.booksapp.backend.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBooks() throws Exception {
        Mockito.when(bookService.getAllBooks(null, null))
               .thenReturn(Arrays.asList(new Book("T1", "A1", "C1", 2000, "", "")));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("T1"));
    }

    @Test
    void testCreateBook_Valid() throws Exception {
        Book book = new Book("T1", "A1", "C1", 2000, "", "");
        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("T1"));
    }

    @Test
    void testCreateBook_InvalidTitle_ReturnsBadRequest() throws Exception {
        Book book = new Book("", "A1", "C1", 2000, "", ""); // Blank title

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest());
    }
}
