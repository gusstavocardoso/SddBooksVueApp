package com.sdd.booksapp.perf.requests;

import io.gatling.javaapi.http.HttpRequestActionBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BookRequests {

    public static HttpRequestActionBuilder getAllBooks = 
        http("Get All Books")
            .get("/api/books")
            .check(status().is(200))
            .check(jsonPath("$[0].id").optional().saveAs("firstBookId"));

    public static HttpRequestActionBuilder filterBooksByCategory = 
        http("Filter Books")
            .get("/api/books?category=Tech")
            .check(status().is(200));

    public static HttpRequestActionBuilder createBook = 
        http("Create Book")
            .post("/api/books")
            .header("Content-Type", "application/json")
            .body(StringBody("{ \"title\": \"#{title}\", \"author\": \"#{author}\", \"category\": \"#{category}\", \"publicationYear\": #{publicationYear}, \"description\": \"#{description}\", \"coverImageUrl\": \"#{coverImageUrl}\" }"))
            .check(status().in(200, 201))
            .check(jsonPath("$.id").saveAs("createdBookId")); // Save the ID to delete later

    public static HttpRequestActionBuilder getBookDetails = 
        http("Get Book Details")
            .get("/api/books/#{createdBookId}")
            .check(status().is(200))
            .check(jsonPath("$.title").is(session -> session.getString("title"))); // Assert the title matches using Session

    public static HttpRequestActionBuilder deleteBook = 
        http("Delete Book")
            .delete("/api/books/#{createdBookId}")
            .check(status().is(204));
}
