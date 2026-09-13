package com.sdd.booksapp.perf.scenarios;

import com.sdd.booksapp.perf.requests.BookRequests;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

public class BookScenarios {

    // Feeder for book data
    public static FeederBuilder<String> bookFeeder = csv("data/books.csv").circular();

    public static ScenarioBuilder browsingScenario = scenario("Browsing Scenario")
        .exec(BookRequests.getAllBooks)
        .pause(Duration.ofSeconds(1), Duration.ofSeconds(3)) // random pause between 1 and 3 seconds
        .exec(BookRequests.filterBooksByCategory)
        .pause(Duration.ofSeconds(1), Duration.ofSeconds(2));

    public static ScenarioBuilder crudScenario = scenario("CRUD Scenario")
        .feed(bookFeeder) // Inject data from CSV
        .exec(BookRequests.getAllBooks)
        .pause(1)
        .exec(BookRequests.createBook)
        .pause(Duration.ofSeconds(1), Duration.ofSeconds(3))
        .exec(BookRequests.getBookDetails) // Uses #{createdBookId} saved during createBook
        .pause(2)
        .exec(BookRequests.deleteBook);
}
