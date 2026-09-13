package com.sdd.booksapp.perf;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BooksSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/Performance Test");

    ScenarioBuilder scn = scenario("Books API Load Test")
            .exec(http("Get All Books")
                    .get("/api/books")
                    .check(status().is(200)))
            .pause(1)
            .exec(http("Get Filtered Books")
                    .get("/api/books?category=Fantasy")
                    .check(status().is(200)));

    {
        setUp(
                scn.injectOpen(rampUsers(50).during(10))
        ).protocols(httpProtocol);
    }
}
